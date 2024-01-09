import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

Mobile.startApplication('C:\\Code\\Python lib\\morello-app\\frontend\\app\\build\\outputs\\apk\\debug\\app-debug.apk', true)

Mobile.sendKeys(findTestObject('Object Repository/Objects UI05/android.widget.EditText'), 'admin@morello.app')

Mobile.sendKeys(findTestObject('Object Repository/Objects UI05/android.widget.EditText (1)'), 'admin')

Mobile.tap(findTestObject('Object Repository/Objects UI05/android.widget.Button'), 0)

Mobile.tap(findTestObject('Object Repository/Objects UI05/android.view.View'), 0)

Mobile.tap(findTestObject('Object Repository/Objects UI05/android.widget.Button (1)'), 0)

Mobile.tap(findTestObject('Object Repository/Objects UI05/android.widget.Button (2)'), 0)

Mobile.sendKeys(findTestObject('Object Repository/Objects UI05/android.widget.EditText - 0'), '.0')

Mobile.sendKeys(findTestObject('Object Repository/Objects UI05/android.widget.EditText (2)'), 'A fund for tax')

Mobile.sendKeys(findTestObject('Object Repository/Objects UI05/android.widget.EditText (3)'), 'everyone must pay up')

Mobile.tap(findTestObject('Object Repository/Objects UI05/android.widget.Button (3)'), 0)

Mobile.tap(findTestObject('Object Repository/Objects UI05/android.widget.TextView - Create'), 0)

Mobile.verifyElementVisible(findTestObject('Object Repository/Objects UI05/android.widget.TextView - A fund for tax'), 0)

Mobile.closeApplication()

