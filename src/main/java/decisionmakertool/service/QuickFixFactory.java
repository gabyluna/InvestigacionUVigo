package decisionmakertool.service;

import decisionmakertool.service.impl.RemoveInvolvedElements;
import decisionmakertool.service.impl.RemoveSimilarElements;
import decisionmakertool.service.impl.RemoveSpecialElements;

public class QuickFixFactory {
    public  QuickFixInterface applyQuickFix(int quickFix){
        QuickFixInterface quickFixInterface = null;
        switch (quickFix) {
            case 1:
                quickFixInterface = new RemoveInvolvedElements();
                break;
            case 2:
                quickFixInterface = new RemoveSimilarElements();
                break;

            case 3:
                quickFixInterface = new RemoveSpecialElements();
                break;

        }
        return quickFixInterface;
    }
}
