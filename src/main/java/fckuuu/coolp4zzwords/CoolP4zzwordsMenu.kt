package fckuuu.coolp4zzwords

import fckuuu.coolp4zzwords.password.PasswordManager
import javafx.scene.input.ClipboardContent
import java.awt.Color
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel


class CoolP4zzwordsMenu(private val key: String) : JFrame("coolp4zzwords") {
    init {
        val passwordManager = PasswordManager()

        this.setBounds(500, 500, 275, 175)
        this.isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE

        val panel = JPanel()
        this.add(panel)

        panel.layout = null
        panel.background = Color(30, 30, 30)

        val getPass = JButton("Get Password")
        panel.add(getPass)

        getPass.foreground = Color.WHITE
        getPass.isContentAreaFilled = false
        getPass.isFocusPainted = false
        getPass.isOpaque = true
        getPass.background = Color(25, 25, 25)

        getPass.setBounds(55, 35, 160, 20)

        getPass.addActionListener {
            val passName = JOptionPane.showInputDialog(null, "Enter password name you wanna get.", "coolp4zzwords", JOptionPane.PLAIN_MESSAGE)
            if (passName == null) { JOptionPane.showMessageDialog(null, "You didn't entered password or cancelled saving.", "coolp4zzwords", JOptionPane.ERROR_MESSAGE) }
            else {
                val pass = passwordManager.getPassword(passName)
                if (pass.equals("Password not found.", false)) JOptionPane.showMessageDialog(null, pass, "coolp4zzwords", JOptionPane.ERROR_MESSAGE)
                else {
                    JOptionPane.showMessageDialog(null, "Copied to clipboard!", "coolp4zzwords", JOptionPane.PLAIN_MESSAGE)
                    copyToClipboard(decrypt(pass))
                }
            }
        }

        val savePass = JButton("Save Password")
        panel.add(savePass)

        savePass.foreground = Color.WHITE
        savePass.isContentAreaFilled = false
        savePass.isFocusPainted = false
        savePass.isOpaque = true
        savePass.background = Color(25, 25, 25)

        savePass.setBounds(55, 75, 160, 20)

        savePass.addActionListener {
            val key = JOptionPane.showInputDialog(null, "Enter your key for password.", "coolp4zzwords", JOptionPane.PLAIN_MESSAGE)
            val savedValue = JOptionPane.showInputDialog(null, "Enter your password.", "coolp4zzwords", JOptionPane.PLAIN_MESSAGE)
            if (key == null || savedValue == null) JOptionPane.showMessageDialog(null, "You didn't enter key/password or cancelled saving.", "coolp4zzwords", JOptionPane.ERROR_MESSAGE)
            else passwordManager.savePassword(key, encrypt(savedValue))
        }
    }

    private fun encrypt(value: String): String {
        val key = SecretKeySpec(key.toByteArray(), "Blowfish")
        val cipher = Cipher.getInstance("Blowfish")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(value.toByteArray())
        return String(encrypted)
    }

    private fun decrypt(value: String): String {
        val key = SecretKeySpec(key.toByteArray(), "Blowfish")
        val cipher = Cipher.getInstance("Blowfish")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decrypted = cipher.doFinal(value.toByteArray())
        return String(decrypted)
    }

    private fun copyToClipboard(value: String) {
        Toolkit.getDefaultToolkit()
            .systemClipboard
            .setContents(
                StringSelection(value),
                null
            )
    }
}
