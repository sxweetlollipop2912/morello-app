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

Mobile.startApplication('/Users/sxweetlollipop/mycode/morello-app/frontend/app/build/outputs/apk/debug/app-debug.apk', true)

Mobile.sendKeys(findTestObject('Object Repository/Login/android.widget.EditText'), 'admin@morello.app')

Mobile.sendKeys(findTestObject('Object Repository/Login/android.widget.EditText (1)'), 'admin')

Mobile.tap(findTestObject('Object Repository/Login/android.widget.Button'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot5541013583813859607.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot3759401428147340148.png')

Mobile.verifyElementExist(findTestObject('Object Repository/Login/android.widget.TextView - Hello, Admin'), 0)

Mobile.closeApplication()

