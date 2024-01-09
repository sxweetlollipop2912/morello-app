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

Mobile.startApplication('C:\\Users\\pc\\OneDrive_VNU_HCMUS\\Documents\\GitHub\\morello-app\\frontend\\app\\build\\outputs\\apk\\debug\\app-debug.apk', 
    true)

Mobile.verifyElementExist(findTestObject('Object Repository/UC04_UI01/android.widget.ImageView'), 0)

Mobile.verifyElementExist(findTestObject('Object Repository/UC04_UI01/android.widget.TextView - Morello'), 0)

Mobile.verifyElementVisible(findTestObject('Object Repository/UC04_UI01/android.widget.TextView - Login'), 0)

Mobile.verifyElementVisible(findTestObject('Object Repository/UC04_UI01/android.widget.TextView - Welcome back Please enter your details'), 
    0)

Mobile.sendKeys(findTestObject('Object Repository/UC04_UI01/android.widget.EditText'), 'test4@morello.app')

Mobile.sendKeys(findTestObject('Object Repository/UC04_UI01/android.widget.EditText (1)'), 'test')

Mobile.tap(findTestObject('Object Repository/UC04_UI01/android.widget.Button'), 0)

Mobile.takeScreenshot('C:\\Users\\pc\\AppData\\Local\\Temp\\screenshot16777083707753120257.png')

Mobile.verifyElementExist(findTestObject('Object Repository/UC04_UI01/android.widget.Button (1)'), 0)

Mobile.verifyElementVisible(findTestObject('Object Repository/UC04_UI01/android.view.View'), 0)

Mobile.closeApplication()

