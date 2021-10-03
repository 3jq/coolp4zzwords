package fckuuu.coolp4zzwords.password

import com.google.gson.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files

class PasswordManager {
    private val passwords = hashMapOf<String, Password>()

    init {
        val file = getDataFile()

        val raw = loadFile(file)
        val json = JsonParser().parse(raw).asJsonObject

        for (password in json.entrySet()) {
            passwords[password.key] = Password(password.value.asString)
        }
    }

    fun savePassword(key: String, password: String) {
        passwords[key] = Password(password)

        val gson = Gson()

        val file = getDataFile()
        if (!file.exists() && !file.createNewFile()) return

        val raw = loadFile(file)
        val oldJson = JsonParser().parse(raw).asJsonObject

        val newJson = JsonObject()

        for (jsonPass in oldJson.entrySet()) { newJson.add(jsonPass.key, jsonPass.value) }

        newJson.add(key, JsonPrimitive(password))

        Files.write(file.toPath(), gson.toJson(JsonParser().parse(newJson.toString())).toByteArray())
    }

    fun getPassword(key: String): String {
        val file = getDataFile()
        if (!file.exists() && !file.createNewFile()) return "Password not found."

        val raw = loadFile(file)
        val json = JsonParser().parse(raw).asJsonObject

        for (password in json.entrySet()) {
            if (password.key.equals(key, true)) return password.value.asString
        }

        return "Password not found."
    }

    private fun loadFile(file: File): String? {
        if (!file.exists()) return "{}"

        val stream = FileInputStream(file.absolutePath)
        val resultStringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(stream)).use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                resultStringBuilder.append(line).append("\n")
            }
        }

        return resultStringBuilder.toString()
    }

    private fun getDataFile(): File {
        return File("data.cp4zz").also { if (loadFile(it)!!.trim() == "") Files.write(it.toPath(), "{}".toByteArray()) }
    }
}