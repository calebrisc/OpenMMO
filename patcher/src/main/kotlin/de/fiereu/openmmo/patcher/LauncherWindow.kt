package de.fiereu.openmmo.patcher

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.nio.file.Files
import java.nio.file.Path
import java.util.prefs.Preferences
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.concurrent.thread

private const val PUBLIC_DEMO_HOST = "login.openmmo.dev"

private data class ServerChoice(val name: String, val host: String?) {
  override fun toString(): String = name
}

internal object LauncherWindow {
  private val preferences = Preferences.userNodeForPackage(LauncherWindow::class.java)

  fun show() {
    SwingUtilities.invokeLater {
      runCatching { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) }
      createFrame().isVisible = true
    }
  }

  private fun createFrame(): JFrame {
    val frame = JFrame("OpenMMO Launcher")
    val serverChoice =
        JComboBox(
            arrayOf(
                ServerChoice("OpenMMO Public Demo", PUBLIC_DEMO_HOST),
                ServerChoice("Custom server", null),
            ))
    val hostField = JTextField(preferences.get("loginHost", PUBLIC_DEMO_HOST), 32)
    val portField = JTextField(preferences.get("loginPort", "2106"), 32)
    val clientField = JTextField(preferences.get("client", defaultClient().toString()), 32)
    val keyField = JTextField(preferences.get("gameKey", defaultGameKey().toString()), 32)
    val logArea = JTextArea(9, 60).apply { isEditable = false }
    val launchButton = JButton("Play OpenMMO")

    val form = JPanel(GridBagLayout())
    form.border = BorderFactory.createEmptyBorder(14, 14, 8, 14)
    addRow(form, 0, "Server", serverChoice)
    addRow(form, 1, "Login address", hostField)
    addRow(form, 2, "Login port", portField)
    addPathRow(form, 3, "PokeMMO client", clientField, frame, true)
    addPathRow(form, 4, "Game public key", keyField, frame, false)

    serverChoice.addActionListener {
      val choice = serverChoice.selectedItem as ServerChoice
      if (choice.host != null) hostField.text = choice.host
      hostField.isEnabled = choice.host == null
    }
    serverChoice.selectedIndex = if (hostField.text == PUBLIC_DEMO_HOST) 0 else 1
    hostField.isEnabled = serverChoice.selectedIndex == 1

    launchButton.addActionListener {
      val host = hostField.text.trim()
      val port = portField.text.trim().toIntOrNull()
      val client = Path.of(clientField.text.trim()).toAbsolutePath().normalize()
      val key = Path.of(keyField.text.trim()).toAbsolutePath().normalize()
      val problem = validate(host, port, client, key)
      if (problem != null) {
        JOptionPane.showMessageDialog(frame, problem, "Cannot launch", JOptionPane.ERROR_MESSAGE)
        return@addActionListener
      }

      preferences.put("loginHost", host)
      preferences.put("loginPort", port.toString())
      preferences.put("client", client.toString())
      preferences.put("gameKey", key.toString())
      launchButton.isEnabled = false
      launchButton.text = "OpenMMO is running"
      logArea.text = ""

      thread(name = "openmmo-launcher") {
        runCatching {
              Launcher.run(
                  LauncherOptions(host, port!!, key, client, client.parent, null, emptyList())) {
                      message ->
                    SwingUtilities.invokeLater {
                      logArea.append("$message\n")
                      logArea.caretPosition = logArea.document.length
                    }
                  }
            }
            .onFailure { error ->
              SwingUtilities.invokeLater {
                logArea.append("Launch failed: ${error.message}\n")
                JOptionPane.showMessageDialog(
                    frame, error.message, "Launch failed", JOptionPane.ERROR_MESSAGE)
              }
            }
        SwingUtilities.invokeLater {
          launchButton.isEnabled = true
          launchButton.text = "Play OpenMMO"
        }
      }
    }

    val footer = JPanel(BorderLayout(8, 8))
    footer.border = BorderFactory.createEmptyBorder(0, 14, 14, 14)
    footer.add(JScrollPane(logArea), BorderLayout.CENTER)
    footer.add(launchButton, BorderLayout.SOUTH)

    frame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
    frame.addWindowListener(
        object : WindowAdapter() {
          override fun windowClosing(event: WindowEvent) {
            if (launchButton.isEnabled) {
              frame.dispose()
            } else {
              JOptionPane.showMessageDialog(
                  frame,
                  "Close PokeMMO before closing the launcher.",
                  "OpenMMO is running",
                  JOptionPane.INFORMATION_MESSAGE,
              )
            }
          }
        })
    frame.layout = BorderLayout()
    frame.add(form, BorderLayout.NORTH)
    frame.add(footer, BorderLayout.CENTER)
    frame.minimumSize = Dimension(680, 420)
    frame.pack()
    frame.setLocationRelativeTo(null)
    return frame
  }

  private fun addRow(panel: JPanel, row: Int, label: String, field: java.awt.Component) {
    panel.add(JLabel(label), constraints(0, row, 0.0))
    panel.add(field, constraints(1, row, 1.0))
  }

  private fun addPathRow(
      panel: JPanel,
      row: Int,
      label: String,
      field: JTextField,
      frame: JFrame,
      executable: Boolean,
  ) {
    val browse = JButton("Browse…")
    browse.addActionListener {
      val chooser = JFileChooser(field.text)
      if (executable) chooser.fileFilter = FileNameExtensionFilter("PokeMMO client", "exe")
      if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
        field.text = chooser.selectedFile.toPath().toString()
      }
    }
    panel.add(JLabel(label), constraints(0, row, 0.0))
    panel.add(field, constraints(1, row, 1.0))
    panel.add(browse, constraints(2, row, 0.0))
  }

  private fun constraints(column: Int, row: Int, weight: Double) =
      GridBagConstraints().apply {
        gridx = column
        gridy = row
        weightx = weight
        fill = GridBagConstraints.HORIZONTAL
        anchor = GridBagConstraints.WEST
        insets = Insets(5, 5, 5, 5)
      }

  private fun validate(host: String, port: Int?, client: Path, key: Path): String? =
      when {
        !host.matches(Regex("[A-Za-z0-9.:-]+")) -> "Enter a valid login address."
        port == null || port !in 1..65535 -> "Enter a valid login port."
        !Files.isRegularFile(client) -> "Select your PokeMMO.exe file."
        !Files.isRegularFile(key) -> "Select the server game.public.pem file."
        else -> null
      }

  private fun defaultClient(): Path {
    val installed = Path.of("C:\\Program Files\\PokeMMO\\PokeMMO.exe")
    return if (Files.isRegularFile(installed)) installed else Path.of("PokeMMO.exe")
  }

  private fun defaultGameKey(): Path {
    val workingKey = Path.of("game.public.pem").toAbsolutePath()
    if (Files.isRegularFile(workingKey)) return workingKey
    val location =
        runCatching {
              Path.of(LauncherWindow::class.java.protectionDomain.codeSource.location.toURI())
            }
            .getOrNull()
    val packagedKey = location?.parent?.parent?.resolve("game.public.pem")
    return packagedKey?.takeIf(Files::isRegularFile) ?: workingKey
  }
}
