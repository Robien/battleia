package core;

import WoodooDragonBase.WoodooDragonBase;
import romainAIv2.RomainAiv21;
import segfault.SegFaultAI;
import tools.Log;
import core.moteur.IAManager;
import core.moteur.IAManager.printStatsToFile;
import dumy.DumyAI;
import dumy.DumyAIVariante;

public class Main
{
    public static void main(String[] args)
    {

        // choix des infos à afficher par la classe Log
        Log.printablePredef();
        IAManager iaManager = new IAManager(printStatsToFile.PAR_IA);

        // ajouter ici son IA
        iaManager.declareIA(new DumyAI());
        iaManager.declareIA(new DumyAIVariante());
        iaManager.declareIA(new RomainAiv21());
        iaManager.declareIA(new WoodooDragonBase());
        iaManager.declareIA(new SegFaultAI());
        // iaManager.declareIA(new MonIAIci());

        // c'est parti !
        iaManager.BOOM();
    }

}
