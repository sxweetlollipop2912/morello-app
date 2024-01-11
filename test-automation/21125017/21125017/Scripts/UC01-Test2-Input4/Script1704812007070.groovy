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

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText'), 'ly2@morello.app')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText (1)'), 'test')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot5333048926146581740.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot14174635674837389691.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button (1)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot8013533245708351957.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot13816888924648196648.png')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText'), '4th')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText (1)'), 'this is a description')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot4840514363987126765.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot15528661847769252163.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot1900469739344975003.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot12504642678714240930.png')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText (2)'), '123')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot2390121379321418416.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot11504796599070706945.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button (2)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot17361522687845663437.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot7821931089899436823.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot1507983586319161668.png')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText (2)'), '1234')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot16038058115743075479.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button (2)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot1997592563287492991.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot17249189441058196941.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button (3)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot9040384166890980969.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot982867431174253319.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot16102520241889139524.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot12106282568514545816.png')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText (2)'), '1234')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot18219604130727407385.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot5188355447643045055.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button (2)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot6382907758358293130.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot11531981262477867330.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button (4)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot5426748708267860035.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot1755119375631915878.png')

Mobile.sendKeys(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.EditText (3)'), '4th')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot14757157639026668622.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot8677438748688934061.png')

Mobile.verifyElementText(findTestObject('UC01-Test2-Input4/android.widget.TextView - my first group'), '4th')

Mobile.verifyElementText(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.TextView - this is a description'), 
    'this is a description')

Mobile.verifyElementText(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.TextView - Owner'), 'Owner')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.view.View'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot9411543177070102062.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot8572321921094311701.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.widget.Button (5)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot7385894874269159423.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot9248272865798529870.png')

Mobile.tap(findTestObject('Object Repository/UC01-Test2-Input1/android.view.View (1)'), 0)

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot9814234100028833602.png')

Mobile.takeScreenshot('/var/folders/9x/mvx2y4qs2836ry3fvrg007l00000gn/T/screenshot2594166406391768303.png')

Mobile.verifyElementText(findTestObject('UC01-Test2-Input4/android.widget.TextView - Mr A'), '123')

Mobile.verifyElementText(findTestObject('UC01-Test2-Input4/android.widget.TextView - Ms B'), '1234')

Mobile.closeApplication()

