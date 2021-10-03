package fckuuu.coolp4zzwords

import java.awt.Color
import javax.swing.*

class KeyMenu : JFrame("coolp4zzwords") {
    init {
        this.setBounds(500, 500, 275, 150)
        this.isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE

        val panel = JPanel()
        this.add(panel)

        panel.layout = null
        panel.background = Color(30, 30, 30)

        val key = JLabel()
        panel.add(key)

        key.text = "Key"
        key.foreground = Color.WHITE

        key.setBounds(120, 10, 130, 20)

        val keyField = JPasswordField("", 10)
        panel.add(keyField)

        keyField.background = Color(30, 30, 30)
        keyField.foreground = Color.LIGHT_GRAY
        keyField.horizontalAlignment = SwingConstants.CENTER
        keyField.border = BorderFactory.createLineBorder(Color.WHITE)
        keyField.caretColor = Color.WHITE

        keyField.setBounds(3, 40, 253, 20)

        val submit = JButton("Submit Key")
        panel.add(submit)

        submit.foreground = Color.WHITE
        submit.isContentAreaFilled = false
        submit.isFocusPainted = false
        submit.isOpaque = true
        submit.background = Color(25, 25, 25)

        submit.setBounds(85, 75, 98, 20)

        submit.addActionListener {
            if (keyField.text.trim().isEmpty()) JOptionPane.showMessageDialog(null, "Key is empty or just filled with spaces.", "coolp4zzwords", JOptionPane.ERROR_MESSAGE)
            else if (keyField.text.toByteArray().size > 56) JOptionPane.showMessageDialog(null, "Key is too big. It should be less than 448 bits.", "coolp4zzwords", JOptionPane.ERROR_MESSAGE)
            else {
                CoolP4zzwordsMenu(keyField.text).isVisible = true
                this.isVisible = false
            }
        }
    }
}
