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

Mobile.takeScreenshot('C:\\Users\\tansa\\AppData\\Local\\Temp\\screenshot11681619649043454598.png')

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI03/android.widget.EditText'), 'admin@morello.app')

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI03/android.widget.EditText (1)'), 'admin')

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.widget.Button'), 0)

Mobile.takeScreenshot('C:\\Users\\tansa\\AppData\\Local\\Temp\\screenshot9247040380917115946.png')

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.view.View'), 0)

Mobile.takeScreenshot('C:\\Users\\tansa\\AppData\\Local\\Temp\\screenshot12377891300602375905.png')

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.widget.Button (1)'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.widget.Button (1)'), 0)

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI03/android.widget.EditText'), '345678')

Mobile.sendKeys(findTestObject('Object Repository/UC03-UI03/android.widget.EditText (1)'), 'Medical Insurance')

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.widget.Button'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.widget.Button (2)'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.widget.Button (3)'), 0)

Mobile.tap(findTestObject('Object Repository/UC03-UI03/android.widget.TextView - Create'), 0)

Mobile.verifyElementExist(findTestObject('Object Repository/UC03-UI03/android.widget.TextView - Error creating balance entry'), 
    0)

Mobile.closeApplication()

