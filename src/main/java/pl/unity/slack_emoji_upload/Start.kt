package pl.unity.slack_emoji_upload

import java.io.File


object Start {
	@JvmStatic
	fun main(vararg args: String) {

		val lastSuccess: String? = null

		SlackLogin(
			driverLocation,
			slackBaseUrl,
			slackUsername,
			slackPassword
		).open {
			val uploader = UploadEmoji(it, slackBaseUrl)
			val emojiPage = "$slackBaseUrl/customize/emoji"
			it.get(emojiPage)
			Thread.sleep(1000)

			File(emotsLocation).listFiles().toList().sorted().forEach { emoji ->
				if (lastSuccess == null || emoji.nameWithoutExtension > lastSuccess) {
					uploader.uploadEmoji(emoji.absolutePath, emoji.nameWithoutExtension)
				}
			}

		}
	}
}



