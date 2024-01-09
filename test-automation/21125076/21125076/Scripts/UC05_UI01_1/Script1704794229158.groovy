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

Mobile.takeScreenshot('C:\\Users\\pc\\AppData\\Local\\Temp\\screenshot14370267972408968830.png')

Mobile.verifyElementExist(findTestObject('Object Repository/UC05_UI01/android.widget.TextView - First time here'), 0)

Mobile.verifyElementVisible(findTestObject('Object Repository/UC05_UI01/android.widget.TextView - Welcome back Please enter your details'), 
    0)

Mobile.tap(findTestObject('Object Repository/UC05_UI01/android.widget.TextView - Sign up'), 0)

Mobile.verifyElementExist(findTestObject('Object Repository/UC05_UI01/android.widget.TextView - Joined us before'), 0)

Mobile.verifyElementVisible(findTestObject('Object Repository/UC05_UI01/android.widget.TextView - Sign up (1)'), 0)

Mobile.sendKeys(findTestObject('Object Repository/UC05_UI01/android.widget.EditText'), 'ducgaren2003@gmail.com')

Mobile.sendKeys(findTestObject('Object Repository/UC05_UI01/android.widget.EditText (1)'), 'dUcGaReN')

Mobile.sendKeys(findTestObject('Object Repository/UC05_UI01/android.widget.EditText (2)'), '24052003')

Mobile.sendKeys(findTestObject('Object Repository/UC05_UI01/android.widget.EditText (3)'), '24052003')

Mobile.tap(findTestObject('Object Repository/UC05_UI01/android.widget.CheckBox'), 0)

Mobile.tap(findTestObject('Object Repository/UC05_UI01/android.widget.Button'), 0)

Mobile.takeScreenshot('C:\\Users\\pc\\AppData\\Local\\Temp\\screenshot179008514906448309.png')

Mobile.sendKeys(findTestObject('UC04_UI01/android.widget.EditText'), 'ducgaren2003@gmail.com')

Mobile.sendKeys(findTestObject('UC04_UI01/android.widget.EditText (1)'), '24052003')

Mobile.tap(findTestObject('Object Repository/UC05_UI01/android.widget.Button'), 0)

Mobile.takeScreenshot('C:\\Users\\pc\\AppData\\Local\\Temp\\screenshot4405475120249368762.png')

Mobile.verifyElementVisible(findTestObject('Object Repository/UC05_UI01/android.view.View'), 0)

Mobile.verifyElementExist(findTestObject('Object Repository/UC05_UI01/android.view.View (1)'), 0)

Mobile.closeApplication()

