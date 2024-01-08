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

Mobile.startExistingApplication('com.example.morello')

Mobile.takeScreenshot('C:\\Users\\tansa\\AppData\\Local\\Temp\\screenshot10202081585241260803.png')

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI01/android.widget.EditText'), 'admin@morello.app')

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI01/android.widget.EditText (1)'), 'admin')

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.widget.Button'), 0)

Mobile.takeScreenshot('C:\\Users\\tansa\\AppData\\Local\\Temp\\screenshot1533464508948811551.png')

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.view.View'), 0)

Mobile.takeScreenshot('C:\\Users\\tansa\\AppData\\Local\\Temp\\screenshot10946887393192006368.png')

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.widget.Button (1)'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.widget.Button (1)'), 0)

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI01/android.widget.EditText'), '100000')

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI01/android.widget.EditText (1)'), 'Buying time for this semester')

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI01/android.widget.EditText (2)'), 'This semester is cursed')

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.widget.Button'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.widget.Button (2)'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.widget.Button (3)'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI01/android.widget.TextView - Create'), 0)

Mobile.takeScreenshot('C:\\Users\\tansa\\AppData\\Local\\Temp\\screenshot8929733198654938877.png')

Mobile.verifyElementVisible(findTestObject('Object Repository/UC03-UI01/android.widget.TextView - family'), 0)

Mobile.verifyElementExist(findTestObject('UC03-UI01/android.view.View (1)'), 0)

Mobile.closeApplication()

