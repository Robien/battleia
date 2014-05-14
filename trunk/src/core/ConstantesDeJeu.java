package core;

/**
 * Cette classe regroupe toutes les constantes utiles au déroulement du jeu et de la gestion de la console.
 * Pour les constantes sur les règles de la parties, il faut aller dans le dossier ressources et regarder le fichier Constantes.java
 * 
 * tout est en public static, vous pouvez les utiliser dans vos IA/main pour faire les tests mais évidement pas dans les versions finales de vos IA !
 */
public class ConstantesDeJeu
{
	public enum e_saveState{NO_SAVE, SAVE_ALL, LOAD};
    // *****************************************************************
    // Déroulement du jeu
    // *****************************************************************

    // ------ conditions de victoire

    // nombre de tours avant la fin de la partie si aucune IA n'a réussie à réaliser les autres objectifs avant
    public static int     nbTourMax         = 100000; // 0 == désactivé
    // Quantitée de métal qu'une IA doit avoir pour finir la partie
    public static int     metalForWin       = 1000000;

    // ------ règles du jeu

    // active ou désactive le système de bonus/malus du temps de calcul de l'IA
    public static boolean isTimeImportant   = false;
    // importance du bonus/malus : si vous êtes 2 fois plus rapide que la moyenne votre production de ressources est multiplié par 2/importanceTemps
    // (donc 20% si importanceTemps = 10)
    public static float   importanceTemps   = 10f;    // plus le chiffre est grand moins le temps est
                                                       // important
    // bonus/malus maximal/minimal. le plus gros bonus est *borneTemps et le plus petit /bornesTemps
    public static float   bornesTemps       = 5f;     // plus le chiffres est petit moins les écarts
                                                       // peuvent être important

    // ------ calcul des constantes

    // est-ce que les constantes doivent-�tre calcul� al�atoirement à chaque d�but de partie ?
    public static boolean random            = false;
    // est-ce qu'il faut sauvegarder tout une valeur de random sauvegard�, aucune ou utiliser la derniere
    public static e_saveState saveState  	= e_saveState.SAVE_ALL;
    // index du seed utilis�, 0 = derni�re sauvegard�
    public static int indexSavedSeedUse  	= 0;
    


    // *****************************************************************
    // Logs
    // *****************************************************************

    // ------ console

    // active/desactive l'affichage des tags dans la console
    public static boolean printTag          = true;
    // d��sactive tout les messages exeptés les messages sans tag, les messages JEU et les messages ERREURS
    public static boolean printOnlyGameInfo = false;
    // désactive tout les messages exeptés les messages qui trichent :-°
    public static boolean mute              = false;

    // ------ Log File

    // chemin à utiliser pour sauvgarder les logs
    public static String  path              = "log/";
}
