package pl.unity.slack_emoji_upload

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class SlackLogin(
	private val driverLocation: String,
	private val slackBaseUrl: String,
	private val slackUsername: String,
	private val slackPassword: String
) {

	private val loginPage = "$slackBaseUrl/"

	private val driver: ChromeDriver

	init {
		System.setProperty("webdriver.chrome.driver", driverLocation)
		driver = ChromeDriver()
	}



	fun open(body: (ChromeDriver) -> Unit) {
		try {
			driver.get(loginPage)

			val email = elementById("email")
			val password = elementById("password")
			val signin = elementById("signin_btn")

			email.sendKeys(slackUsername)
			password.sendKeys(slackPassword)
			signin.click()

			Thread.sleep(2000)

			body(driver)
		}
		finally {
			driver.quit()
		}
	}

	private fun elementById(s: String) = wait().until(ExpectedConditions.presenceOfElementLocated(By.id(s)))

	private fun wait() = WebDriverWait(driver, 10)
}