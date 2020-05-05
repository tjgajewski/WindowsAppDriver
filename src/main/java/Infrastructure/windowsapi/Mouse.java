package infrastructure.windowsapi;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public class Mouse {

    public static void leftClick(){
        pressButton(0x0002);
        releaseButton(0x0004);
    }

    public static void rightClick(){

    }

    protected static void pressButton(long key){
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");
        input.input.mi.time = new WinDef.DWORD(0);
        input.input.mi.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.mi.dwFlags = new WinDef.DWORD(0);
        Keyboard.checkStatus(User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size()), key);
    }

    protected static void releaseButton(long key){
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(key);
        input.input.ki.dwFlags = new WinDef.DWORD(2);
        Keyboard.checkStatus(User32.INSTANCE.SendInput( new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size()), key);
    }

}
