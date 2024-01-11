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

Mobile.startApplication('/Users/sxweetlollipop/Downloads/app-debug.apk', true)

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.EditText'), 'ly@morello.app')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.EditText (1)'), 'test')

Mobile.tap(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.Button'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot2090960684232398705.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot14896275533656777981.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.Button (1)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot15784667172917290255.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot15838998315858354786.png')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.EditText'), 'fifth group 123 hello !@#')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.EditText (1)'), 'Wild desc _ e$#%!!!!')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot6854766208309507618.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot11450212685465970035.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.Button (2)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot2762136122342655947.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot6965185706352029435.png')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.EditText (2)'), 'fifth group 123 hello !@#')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot11981577875323467590.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot2439326565427201811.png')

Mobile.verifyElementText(findTestObject('UC01-Test1-Input5/android.widget.TextView - fifth group 123 hello'), 'fifth group 123 hello !@#')

Mobile.verifyElementText(findTestObject('UC01-Test1-Input5/android.widget.TextView - Wild desc _'), 'Wild desc _ e$#%!!!!')

Mobile.verifyElementText(findTestObject('Object Repository/UC01-Test1-Input1/android.widget.TextView - Owner'), 'Owner')

Mobile.closeApplication()

