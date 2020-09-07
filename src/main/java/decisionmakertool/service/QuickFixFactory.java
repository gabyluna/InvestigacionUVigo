package decisionmakertool.service;

import decisionmakertool.entities.QuickFixModel;
import decisionmakertool.service.impl.RemoveInvolvedElements;
import decisionmakertool.service.impl.RemoveSimilarElements;

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

        }
        return quickFixInterface;
    }
}
