package core.moteur;

import java.util.ArrayList;
import java.util.HashMap;

import tools.InfosToPrint;
import tools.Log;
import tools.Log.tag;
import tools.LogFile;
import api.IA.AbstractIA;
import api.IA.InfosBase;
import api.ressources.Environement;
import api.ressources.elements.Groupe;
import core.ConstantesDeJeu;
import core.ConstantesDeJeu.e_saveState;
import core.ressources.Constantes;
import core.ressources.Constantes.typeBatiment;
import core.ressources.Constantes.typeRessource;
import core.ressources.IAMoteur;
import core.ressources.InfosBaseMoteur;

// gestionaire d'IA
public class IAManager
{

    private ArrayList<AbstractIA>                           ias            = new ArrayList<>();

    private HashMap<AbstractIA, ArrayList<InfosBaseMoteur>> bases          = new HashMap<>();
    private HashMap<AbstractIA, IAMoteur>                   iaMoteurs      = new HashMap<>();
    private HashMap<Integer, InfosBaseMoteur>               basesParID     = new HashMap<>();
    private HashMap<AbstractIA, Long>                       temps          = new HashMap<>();
    private static int                                      idBase         = 0;
    private boolean                                         stop           = false;

    private int                                             maxLvl         = 0;
    private printStatsToFile                                statLvl;
    private LogFile                                         log;

    private TerrainManager                                  terrainManager = new TerrainManager();

    public enum printStatsToFile
    {
        NONE, PAR_IA, PAR_BASE
    }

    /**
     * constructeur de IAManager
     * 
     * @param statLvl
     *            le niveau de stat écrit dans des fichiers
     */
    public IAManager(printStatsToFile statLvl)
    {
        this.statLvl = statLvl;
        if (this.statLvl != printStatsToFile.NONE)
        {
            log = new LogFile();
        }
    }

    /**
     * declare l'IA pour qu'elle participe à la battle
     * 
     * @param ia
     *            l'IA à ajouté
     */
    public void declareIA(AbstractIA ia)
    {
        ias.add(ia);
        if (statLvl == printStatsToFile.PAR_IA || statLvl == printStatsToFile.PAR_BASE)// en fait c'est la même chose pour l'instant...
        {
            log.add(ia);
        }
    }

    // lance le jeu
    public void BOOM(int loadSavedGame)
    {
        e_saveState lastState = ConstantesDeJeu.saveState;
        ConstantesDeJeu.indexSavedSeedUse = loadSavedGame;
        ConstantesDeJeu.saveState = e_saveState.LOAD;
        BOOM();
        ConstantesDeJeu.saveState = lastState;
        ConstantesDeJeu.indexSavedSeedUse = 0;
    }

    // lance le jeu
    public void BOOM()
    {
        Log.print(tag.IAMANAGER, "init constantes");
        // initialisation des constantes
        Constantes.init();
        Environement.init();

        Log.print(tag.IAMANAGER, "creation des bases");
        // creation des bases pour chaque IA.
        init();

        Log.print(tag.IAMANAGER, "creation du cache");
        DBCache cache = new DBCache();

        // on commence le tour
        for (int tour = 0; !stop && (ConstantesDeJeu.nbTourMax == 0 || tour < ConstantesDeJeu.nbTourMax); tour++)
        {
            Log.print(tag.JEU, "debut du tour " + tour);

            // System.out.println("purge du cache");
            cache.purge();
            double tempsMoyen = 0;
            for (final AbstractIA ia : ias)
            {
                // System.out.println("préparation pour l'IA : " + ia.getName());
                final ArrayList<InfosBase> basesIA = new ArrayList<>();

                for (InfosBaseMoteur infosBase : bases.get(ia))
                {
                    infosBase.population = Constantes.get().getProd(infosBase.lvlFerme, typeRessource.POPULATION) - infosBase.peonEnVoyage;
                    // System.out.println("copie de la base id " + infosBase.idBase);
                    InfosBase base = new InfosBase(infosBase, terrainManager);
                    basesIA.add(base);
                    // System.out.println("calcul du cache");
                    cache.addInfoBase(base);
                    infosBase.rel = base;
                }

                // copie des groupes pour le joueur
                cache.addGroupes(iaMoteurs.get(ia).getGroupesCopy(terrainManager), ia);

                Environement.get().setCache(cache);

                Log.print(tag.IAMANAGER, "début du calcul de l'IA");
                // Thread threadIA = new Thread()
                // {
                // @Override
                // public void run()
                // {
                //
                // ia.nouveauTour(basesIA);
                // }
                // };
                long debut = System.nanoTime();
                // threadIA.start();
                try
                {
                    if (!ia.isDiqualifie)
                    {
                        ia.nouveauTour(basesIA);
                    }
                }
                catch (Exception e)
                {
                    Log.print(tag.ERREUR, "IA " + ia.getName() + " disqualifié !");
                    e.printStackTrace();
                    ia.isDiqualifie = true;
//                    return;
                }
                // try
                // {
                // threadIA.join();
                // }
                // catch (InterruptedException e)
                // {
                // e.printStackTrace();
                // }
                long fin = System.nanoTime();

                ia.tempsDeCalcul += (fin - debut);

                Log.print(tag.IAMANAGER, "fin calcul de l'IA ");
                Log.print(tag.STATS, "temps de calcul de l'IA " + ia.getName() + " : " + ((fin - debut) / 1000) + "us");
                tempsMoyen += ((fin - debut) / (float) ias.size());
                if (fin == debut)
                {
                    temps.put(ia, 1l);
                }
                else
                {
                    temps.put(ia, fin - debut);
                }
            }

            // déplacement des unitées

            for (AbstractIA ia : ias)
            {
                // avancée des groupes d'un tour
                iaMoteurs.get(ia).avanceDUnTour(terrainManager);
            }


//            HashMap<Integer, HashMap<Integer, String>> invrai = new HashMap<>();
//            for (AbstractIA ia : ias)
//            {
//                for (Groupe g : iaMoteurs.get(ia).groupes)
//                {
//                    if (invrai.get(g.getPosition().getPosX()) == null)
//                    {
//                        invrai.put(g.getPosition().getPosX(), new HashMap<Integer, String>());
//                    }
//                    invrai.get(g.getPosition().getPosX()).put(g.getPosition().getPosY(), "G");
////                    if (g.getDestination() != null)
////                    {
////                        Log.print(tag.JEU, "Ajout pos " + g.getDestination().getCoordonneesPrintable());
////                    }
//
//                }
//
//            }
//
//            terrainManager.print(invrai);

            Log.print(tag.IAMANAGER, "calcul du tour");
            // on fait le calcul du tour
            for (AbstractIA ia : ias)
            {

                Log.print(tag.IAMANAGER, "ia : " + ia.getName());
                for (InfosBaseMoteur infosBaseMoteur : bases.get(ia))
                {

                    for (Groupe g : infosBaseMoteur.rel.nouveauxGroupes)
                    {
                        infosBaseMoteur.groupes.put(g.getId(), new Groupe(g, infosBaseMoteur.rel));
                    }

                    if (infosBaseMoteur.rel.popBucheron < 0)
                    {
                        infosBaseMoteur.rel.popBucheron = 0;
                        Log.print(tag.ERREUR, "Nombre de travailleur négatif !");
                    }
                    if (infosBaseMoteur.rel.popMine < 0)
                    {
                        infosBaseMoteur.rel.popMine = 0;
                        Log.print(tag.ERREUR, "Nombre de travailleur négatif !");
                    }
                    if (infosBaseMoteur.rel.popCarriere < 0)
                    {
                        infosBaseMoteur.rel.popCarriere = 0;
                        Log.print(tag.ERREUR, "Nombre de travailleur négatif !");
                    }

                    float proportionRessources = 1;
                    if (ConstantesDeJeu.isTimeImportant)
                    {
                        proportionRessources = ((float) (tempsMoyen / temps.get(ia)) - 1f) / ConstantesDeJeu.importanceTemps + 1f;
                        proportionRessources = Math.min(ConstantesDeJeu.bornesTemps, proportionRessources);
                        proportionRessources = Math.max(1 / ConstantesDeJeu.bornesTemps, proportionRessources);
                        // System.out.println(proportionRessources);
                        // System.out.println("= " + temps.get(ia));
                    }

                    // occupons nous maintenant de la production de ressources.
                    if (Environement.get().getCoutPop(typeBatiment.BUCHERON, infosBaseMoteur.rel) != 0)
                    {
                        if ((infosBaseMoteur.rel.popBucheron) <= Environement.get().getCoutPop(typeBatiment.BUCHERON, infosBaseMoteur.rel))
                        {
                            infosBaseMoteur.quantiteBois += Environement.get().RAWgetProdFloat(typeRessource.BOIS, infosBaseMoteur.rel)
                                    * proportionRessources * (infosBaseMoteur.rel.popBucheron)
                                    / Environement.get().getCoutPop(typeBatiment.BUCHERON, infosBaseMoteur.rel);
                        }
                        else
                        {
                            Log.print(tag.ERREUR, "Production de bois, trop de travailleurs !");
                            infosBaseMoteur.quantiteBois += proportionRessources
                                    * Environement.get().RAWgetProdFloat(typeRessource.BOIS, infosBaseMoteur.rel);
                        }
                    }
                    if (Environement.get().getCoutPop(typeBatiment.MINE, infosBaseMoteur.rel) != 0)
                    {
                        if ((infosBaseMoteur.rel.popMine) <= Environement.get().getCoutPop(typeBatiment.MINE, infosBaseMoteur.rel))
                        {
                            infosBaseMoteur.quantiteMetal += Environement.get().RAWgetProdFloat(typeRessource.METAL, infosBaseMoteur.rel)
                                    * proportionRessources * (infosBaseMoteur.rel.popMine)
                                    / Environement.get().getCoutPop(typeBatiment.MINE, infosBaseMoteur.rel);
                        }
                        else
                        {
                            Log.print(tag.ERREUR, "Production de métal, trop de travailleurs !");
                            infosBaseMoteur.quantiteMetal += proportionRessources
                                    * Environement.get().RAWgetProdFloat(typeRessource.METAL, infosBaseMoteur.rel);
                        }

                    }
                    if (Environement.get().getCoutPop(typeBatiment.CARRIERE, infosBaseMoteur.rel) != 0)
                    {
                        if ((infosBaseMoteur.rel.popCarriere) <= Environement.get().getCoutPop(typeBatiment.CARRIERE, infosBaseMoteur.rel))
                        {
                            infosBaseMoteur.quantitePierre += Environement.get().RAWgetProdFloat(typeRessource.PIERRE, infosBaseMoteur.rel)
                                    * proportionRessources * (infosBaseMoteur.rel.popCarriere)
                                    / Environement.get().getCoutPop(typeBatiment.CARRIERE, infosBaseMoteur.rel);
                        }
                        else
                        {
                            Log.print(tag.ERREUR, "Production de pierre, trop de travailleurs !");
                            infosBaseMoteur.quantitePierre += proportionRessources
                                    * Environement.get().RAWgetProdFloat(typeRessource.PIERRE, infosBaseMoteur.rel);
                        }

                    }
                    if (infosBaseMoteur.quantiteMetal >= ConstantesDeJeu.metalForWin)
                    {
                        stop = true;
                    }

                    // gestion des groupes

                    // copie des nouveaux groupes
                    for (Groupe g : infosBaseMoteur.rel.nouveauxGroupes)
                    {
                        iaMoteurs.get(ia).groupes.add(g);
                        // Log.print(tag.JEU, "ajout des cases");
                        // Log.print(tag.JEU,infosBaseMoteur.caseBase.getCoordonneesPrintable());
                        // for (Case[] c1 : terrainManager.getCasesAutour(infosBaseMoteur.caseBase))
                        // {
                        // for (Case case1 : c1)
                        // {
                        // Log.print(tag.JEU,case1.getCoordonneesPrintable());
                        // }
                        // }
                        // Log.print(tag.JEU,terrainManager.getCasesAutour(infosBaseMoteur.caseBase)[1][1].getCoordonneesPrintable());
                        g.setAutour(terrainManager.getCasesAutour(infosBaseMoteur.caseBase));
                        infosBaseMoteur.addGroupe(g);
                        // TODO: verif
                        // TODO: cost
                    }
                    
                    //copie des nouvelles destinations
                    for (Groupe g : Environement.get().getGroupes(ia).values())
                    {
                        iaMoteurs.get(ia).majDestination(g);
                    }

                    // System.out.println("construction...");
                    // si il y a une construction en cours
                    if (infosBaseMoteur.rel.constructionEnCours != typeBatiment.NONE)
                    {
                        infosBaseMoteur.timeConstruct++;
                        // System.out.println("construction EN COURS !!! ...");
                        // si elle viens de démarer
                        if (infosBaseMoteur.constructionEnCours != infosBaseMoteur.rel.constructionEnCours)
                        {
                            if (Environement.get().isConstructionPossible(infosBaseMoteur.rel.constructionEnCours, infosBaseMoteur.rel))
                            {

                                infosBaseMoteur.constructionEnCours = infosBaseMoteur.rel.constructionEnCours;
                                infosBaseMoteur.quantiteBois -= Environement.get().getCoutAmelioration(infosBaseMoteur.rel.constructionEnCours,
                                        typeRessource.BOIS, infosBaseMoteur.rel);
                                infosBaseMoteur.quantiteMetal -= Environement.get().getCoutAmelioration(infosBaseMoteur.rel.constructionEnCours,
                                        typeRessource.METAL, infosBaseMoteur.rel);
                                infosBaseMoteur.quantitePierre -= Environement.get().getCoutAmelioration(infosBaseMoteur.rel.constructionEnCours,
                                        typeRessource.PIERRE, infosBaseMoteur.rel);
                                infosBaseMoteur.tempsEcouleDepuisDebutConstruction = Environement.get().getCoutAmelioration(
                                        infosBaseMoteur.rel.constructionEnCours, typeRessource.TEMPS, infosBaseMoteur.rel);
                                // System.out.println("temps avant fin :" + infosBaseMoteur.tempsEcouleDepuisDebutConstruction);
                            }
                            else
                            {
                                Log.print(tag.ERREUR, "Pas assez de ressources pour faire la construction !");
                                infosBaseMoteur.rel.constructionEnCours = typeBatiment.NONE;
                            }
                        }
                        else
                        {
                            // la construction était déjà là au tour d'avant
                        }
                        // dans tout les cas on réduit d'un tour le temps de construction
                        infosBaseMoteur.tempsEcouleDepuisDebutConstruction--;
                        // System.out.println("temps avant fin :" + infosBaseMoteur.tempsEcouleDepuisDebutConstruction);
                        if (infosBaseMoteur.tempsEcouleDepuisDebutConstruction <= 0)
                        {
                            // la construction est terminée.
                            // System.out.println("lvl du " + infosBaseMoteur.rel.constructionEnCours.toString() + " : "
                            // + infosBaseMoteur.getLvl(infosBaseMoteur.rel.constructionEnCours));
                            infosBaseMoteur.addLvl(infosBaseMoteur.rel.constructionEnCours);
                            maxLvl = Math.max(maxLvl, infosBaseMoteur.getLvl(infosBaseMoteur.rel.constructionEnCours));
                            if (2 * maxLvl > Constantes.get().getValues().getSize())
                            {
                                Constantes.get().getValues().compute(Constantes.get().getValues().getSize() * 2);
                            }
                            // System.out.println("lvl du " + infosBaseMoteur.rel.constructionEnCours.toString() + " : "
                            // + infosBaseMoteur.getLvl(infosBaseMoteur.rel.constructionEnCours));
                            infosBaseMoteur.constructionEnCours = typeBatiment.NONE;

                        }

                    }
                    else
                    {
                        // pas de construction en cours
                        infosBaseMoteur.timePasConstruct++;
                    }

                    Log.print(tag.STATS, "BASEID:" + infosBaseMoteur.idBase);
                    Log.print(tag.STATS, "ressources:" + (int) infosBaseMoteur.quantiteBois + "/" + (int) infosBaseMoteur.quantitePierre + "/"
                            + (int) infosBaseMoteur.quantiteMetal);
                    Log.print(tag.STATS, "batiments:" + infosBaseMoteur.lvlBucheron + "/" + infosBaseMoteur.lvlCarriere + "/"
                            + infosBaseMoteur.lvlMine + "/" + infosBaseMoteur.lvlFerme);
                    Log.print(tag.STATS, "temps total construction : " + infosBaseMoteur.timeConstruct + " temps attente : "
                            + infosBaseMoteur.timePasConstruct);

                    if (statLvl != printStatsToFile.NONE)
                    {

                        InfosToPrint info = new InfosToPrint(infosBaseMoteur);
                        info.time = temps.get(ia);
                        info.tempsCumule = ia.tempsDeCalcul;
                        if (Environement.get().getCoutPop(typeBatiment.BUCHERON, infosBaseMoteur.rel) != 0)
                        {
                            info.prodBois = 100 * infosBaseMoteur.rel.popBucheron
                                    / Environement.get().getCoutPop(typeBatiment.BUCHERON, infosBaseMoteur.rel);
                        }
                        if (Environement.get().getCoutPop(typeBatiment.CARRIERE, infosBaseMoteur.rel) != 0)
                        {
                            info.prodPierre = 100 * infosBaseMoteur.rel.popCarriere
                                    / Environement.get().getCoutPop(typeBatiment.CARRIERE, infosBaseMoteur.rel);
                        }
                        if (Environement.get().getCoutPop(typeBatiment.MINE, infosBaseMoteur.rel) != 0)
                        {
                            info.prodMetal = 100 * infosBaseMoteur.rel.popMine
                                    / Environement.get().getCoutPop(typeBatiment.MINE, infosBaseMoteur.rel);
                        }
                        log.writeStats(ia, info);
                    }

                }
            }
        }

        // initialisation des résultats (ça ne fait quelque chose qu'au premier appel !)
        Resultats.get().init(ias);
        // ajout des résultats pour cette partie
        Resultats.get().addResult(bases, temps);

        // on recherche le/les gagnants
        Log.print(tag.JEU, "Fin de la partie !");

        double tempsMin = Double.MAX_VALUE;
        for (AbstractIA ia : ias)
        {
            tempsMin = Math.min(tempsMin, ia.tempsDeCalcul);
        }
        int maxMetal = 0;
        ArrayList<AbstractIA> gagant = new ArrayList<>();
        for (AbstractIA ia : ias)
        {
            int totalMetal = 0;
            for (InfosBaseMoteur infosBase : bases.get(ia))
            {
                totalMetal += infosBase.quantiteMetal;
                // System.out.println(infosBase.quantiteMetal);
            }
            Log.print(tag.JEU, "total ia \"" + ia.getName() + "\" : " + totalMetal + " (" + (int) (ia.tempsDeCalcul / 1000000) + "ms - "
                    + ((int) (ia.tempsDeCalcul / tempsMin * 100)) + "%)");
            if (totalMetal > maxMetal)
            {
                gagant.clear();
                maxMetal = totalMetal;
            }
            if (totalMetal >= maxMetal)
            {
                gagant.add(ia);
            }
        }

        Log.print(tag.JEU, "Il y a " + gagant.size() + " gagnant !");
        for (AbstractIA ia : gagant)
        {
            Log.print(tag.JEU, ia.getName());
        }
        Log.printNbErreur();

        if (statLvl != printStatsToFile.NONE)
        {
            log.close();
        }
    }

    private void init()
    {
        // initialisation des ID des base
        idBase = 1;
        // une base pour chacun. Comme c'est gentil.
        for (AbstractIA ia : ias)
        {
            ArrayList<InfosBaseMoteur> basesIA = new ArrayList<>();

            InfosBaseMoteur ibm = infosBaseMoteurFactory(ia);
            basesIA.add(ibm);
            basesParID.put(ibm.idBase, ibm);

            // ibm = infosBaseMoteurFactory(ia);
            // basesIA.add(ibm);
            // basesParID.put(ibm.idBase, ibm);

            bases.put(ia, basesIA);

            iaMoteurs.put(ia, new IAMoteur(ia));
        }

        // génération du terrain
        terrainManager.build(bases.values());
        // terrainManager.print();

    }

    private InfosBaseMoteur infosBaseMoteurFactory(AbstractIA ia)
    {
        InfosBaseMoteur res = new InfosBaseMoteur(idBase, Constantes.get().departBois, Constantes.get().departPierre, Constantes.get().departMetal,
                0, 0, 0, 0, 0, 0, 0, 0, typeBatiment.NONE, 0, ia);
        idBase++;
        return res;
    }

}
