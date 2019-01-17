package pl.unity.slack_emoji_upload

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import java.io.File
import java.lang.RuntimeException


object Start {
	@JvmStatic
	fun main(vararg args: String) {

		val lastSuccess: String? = null

		val options = Options().apply {
			addRequiredOption("d", "driver", true,
				"Driver file of selenium chrome dirver")
			addRequiredOption("e", "emojis", true,
				"Directory with emojis. It should contain only files meant to upload to slack and no " +
					"subdirectories. Name of file will be used as an name for emoji. For example wat.png will be uploaded " +
					" as :wat: slack emoji.")
			addRequiredOption("u", "username", true, "Username to log into slack.")
			addRequiredOption("p", "password", true, "Password to log into slack.")
			addRequiredOption("s", "url", true, "Slack base url (full url). For example " +
				"https://myworkspace.slack.com")
		}

		val commandLine = try {
			DefaultParser().parse(options, args)
		} catch (e: Exception) {
			HelpFormatter().printHelp("slack-emoji-upload.sh", options)
			System.exit(1)
			throw RuntimeException("This code is unreachable, but compiler doesnt understand it (shrug)")
		}

		val driverLocation: String = commandLine.getOptionValue("driver")
		val slackBaseUrl: String = commandLine.getOptionValue("url")
		val slackUsername: String = commandLine.getOptionValue("username")
		val slackPassword: String = commandLine.getOptionValue("password")
		val emotsLocation: String = commandLine.getOptionValue("emoji")

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



