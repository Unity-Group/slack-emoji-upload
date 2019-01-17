package pl.unity.slack_emoji_upload

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class UploadEmoji(
	private val driver: ChromeDriver,
	private val slackBaseUrl: String
) {

	private val emojiPage = "$slackBaseUrl/customize/emoji"

	fun uploadEmoji(fileLocation: String, emojiName: String) {
		try {
//			driver.get(emojiPage)

			elementByCss("button[emoji-type='emoji']").click()

			wait().until(ExpectedConditions.presenceOfElementLocated(By.id("emojiimg"))).sendKeys(fileLocation)

			elementById("emojiname").sendKeys(emojiName)

			Thread.sleep(500)
			if (!elementByCss("button[data-qa='customize_emoji_add_dialog_go']").isEnabled) {
				throw Exception("Button disabled - sad panda :(")
			}

			elementByCss("button[data-qa='customize_emoji_add_dialog_go']").click()

			Thread.sleep(1000)

			if(driver.findElements(By.cssSelector(
					"div[data-qa='customize_emoji_add_dialog_header'] .c-dialog__close i"))
					.size > 0) {
				println("Popup is visible!")
				throw Exception("Popup still open - guess we failed")
			}

			println("Probably uploaded $emojiName")
		} catch (e: Exception) {
			println("Failed to upload $emojiName")
			e.printStackTrace()
			try {
				elementByCss("div[data-qa='customize_emoji_add_dialog_header'] .c-dialog__close i").click()
			} catch (e: Exception) {
				println("Refreshing page")
				driver.get(emojiPage)
			}
		}
	}

	private fun elementById(s: String) = wait().until(ExpectedConditions.visibilityOfElementLocated(By.id(s)))

	private fun elementByCss(s: String) = wait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(s)))

	private fun wait() = WebDriverWait(driver, 10)
}