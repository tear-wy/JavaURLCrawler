package Main;

import Frame.BasicFrame;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import javax.swing.*;

public class main {
    public static void main(String[] args) {
        try
        {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
        }
        catch(Exception e)
        {
            System.out.println("UI jar Error");
        }
        new BasicFrame();
    }
}
