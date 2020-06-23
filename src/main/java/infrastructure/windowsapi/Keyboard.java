package infrastructure.windowsapi;

import com.gargoylesoftware.htmlunit.NotYetImplementedException;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import org.openqa.selenium.ElementNotInteractableException;


public class Keyboard {

    public static void sendInput(CharSequence... charSequences){
        Boolean holdingShift = false;
        Boolean holdingAlt = false;
        Boolean holdingCtrl = false;
        for(CharSequence charSequence : charSequences){
            for(int i = 0; i < charSequence.length(); i++){
                char c = charSequence.charAt(i);
                long vk = charToVKCode(c);
                if(Character.isUpperCase(c)){
                    pressKey(0x10);
                    pressKey(vk);
                    releaseKey(vk);
                    releaseKey(0x10);
                }
                else if (c == CONTROL_KEY){
                        pressKey(vk);
                        holdingCtrl = true;
                }
                else if (c == SHIFT_KEY){
                        pressKey(vk);
                        holdingShift = true;
                }
                else if (c == ALT_KEY){
                        pressKey(vk);
                        holdingAlt = true;
                }
                else if (c == RELEASE){
                    if (holdingAlt) {
                        releaseKey(0x12);
                    }
                    else if (holdingCtrl) {
                        releaseKey(0x11);
                    }
                    else if (holdingShift) {
                        releaseKey(0x10);
                    }
                }
                else {
                    pressKey(vk);
                    releaseKey(vk);
                }
            }

        }
    }

    protected static void pressKey(long key){
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(key);
        input.input.ki.dwFlags = new WinDef.DWORD(0);
        checkStatus(User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size()), key);
    }

    protected static void releaseKey(long key){
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(key);
        input.input.ki.dwFlags = new WinDef.DWORD(2);
        checkStatus(User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size()), key);
    }
    public static final char CONTROL_KEY = '\uE009';
    public static final char ENTER_KEY = '\uE007';
    public static final char TAB_KEY = '\uE004';
    public static final char ALT_KEY = '\uE00A';
    public static final char UP_KEY = '\uE013';
    public static final char LEFT_KEY = '\uE012';
    public static final char RIGHT_KEY = '\uE014';
    public static final char DOWN_KEY = '\uE015';
    public static final char SHIFT_KEY = '\uE008';
    public static final char RELEASE = '\uE000';

    public static final char F1_KEY = '\uE031';
    public static final char F2_KEY = '\uE032';
    public static final char F3_KEY = '\uE033';
    public static final char F4_KEY = '\uE034';
    public static final char F5_KEY = '\uE035';
    public static final char F6_KEY = '\uE036';
    public static final char F7_KEY = '\uE037';
    public static final char F8_KEY = '\uE038';
    public static final char F9_KEY = '\uE039';
    public static final char F10_KEY = '\uE03A';
    public static final char F11_KEY = '\uE03B';
    public static final char F12_KEY = '\uE03C';
    private static long charToVKCode(Character c){
        switch (c){
            case ENTER_KEY:
                return 0x0D;
            case TAB_KEY:
                return 0x09;
            case CONTROL_KEY:
                return 0x11;
            case ALT_KEY:
                return 0x12;
            case UP_KEY:
                return 0x26;
            case LEFT_KEY:
                return 0x25;
            case RIGHT_KEY:
                return 0x27;
            case DOWN_KEY:
                return 0x28;
            case SHIFT_KEY:
                return 0x10;
            case F1_KEY:
                return 0x70;
            case F2_KEY:
                return 0x71;
            case F3_KEY:
                return 0x72;
            case F4_KEY:
                return 0x73;
            case F5_KEY:
                return 0x74;
            case F6_KEY:
                return 0x75;
            case F7_KEY:
                return 0x76;
            case F8_KEY:
                return 0x77;
            case F9_KEY:
                return 0x78;
            case F10_KEY:
                return 0x79;
            case F11_KEY:
                return 0x7A;
            case F12_KEY:
                return 0x7B;
            case ' ':
                return 0x20;
            case 'a' :
            case 'A' :
                return 0x41;
            case 'b' :
            case 'B' :
                return 0x42;
            case 'c' :
            case 'C' :
                return 0x43;
            case 'd' :
            case 'D' :
                return 0x44;
            case 'e' :
            case 'E' :
                return 0x45;
            case 'f' :
            case 'F' :
                return 0x46;
            case 'g' :
            case 'G' :
                return 0x47;
            case 'h' :
            case 'H' :
                return 0x48;
            case 'i' :
            case 'I' :
                return 0x49;
            case 'j' :
            case 'J' :
                return 0x4A;
            case 'k' :
            case 'K' :
                return 0x4B;
            case 'l' :
            case 'L' :
                return 0x4C;
            case 'm' :
            case 'M' :
                return 0x4D;
            case 'n' :
            case 'N' :
                return 0x4E;
            case 'o' :
            case 'O' :
                return 0x4F;
            case 'p' :
            case 'P' :
                return 0x50;
            case 'q' :
            case 'Q' :
                return 0x51;
            case 'r' :
            case 'R' :
                return 0x52;
            case 's' :
            case 'S' :
                return 0x53;
            case 't' :
            case 'T' :
                return 0x54;
            case 'u' :
            case 'U' :
                return 0x55;
            case 'v' :
            case 'V' :
                return 0x56;
            case 'w' :
            case 'W' :
                return 0x57;
            case 'x' :
            case 'X' :
                return 0x58;
            case 'y' :
            case 'Y' :
                return 0x59;
            case 'z' :
            case 'Z' :
                return 0x5A;
            case '0' :
                return 0x30;
            case '1' :
                return 0x31;
            case '2' :
                return 0x32;
            case '3' :
                return 0x33;
            case '4' :
                return 0x34;
            case '5' :
                return 0x35;
            case '6' :
                return 0x36;
            case '7' :
                return 0x37;
            case '8' :
                return 0x38;
            case '9' :
                return 0x39;
            case '\\':
                return 0xDC;
            case '/':
                return 0x6F;
            case '.':
                return 0xBE;
            case RELEASE:
                return 0000;
            default :
                throw new NullPointerException("Unimplemented Character Value for "+c);
        }

    }

protected static void checkStatus(WinDef.DWORD statusCode, long key){
        if(statusCode.intValue()<1){
            throw new ElementNotInteractableException("Failed to send key code too element " + key);
        }
}
}
