package reschu.tutorial;

import javax.swing.JOptionPane;

import reschu.app.AppMain;
import reschu.constants.MyDB;

public class Scenario2 extends TutorialModel {
	
	Scenario2(AppMain main) {
		super(main);
	}
	
       protected void showDialog() {
               switch(getState()) {
                   case 0:
                       JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_0.png",
                                                       ""));
                               //Intro: Explains the Purpose of the game
                               setDuration(1);
                               nextDialog();
                               break;
                   
                       case 1:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_1.png",
                                                       ""));
                               //Intro: Explains the Purpose of the game
                               setDuration(1);
                               nextDialog();
                               break;
                       case 2:
                           JOptionPane.showMessageDialog(null,
                                   makePicPanel("2_2.png",""));
                           //Step_2: Explains the Interface Elements
                           setDuration(3);
                           nextDialog();
                           break;
                       case 3:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_3.png",""));
                               //Step_1: Explains the Map elements
                               setDuration(3);
                               nextDialog();
                               break;
                       case 4:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_4.png",""));
                               //Step_3: Explains How to change a vehicle's destination
                               setDuration(25);
                               break;
                       case 5:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_5.png",""));
                               //Step_4: Explains How to add a waypoint
                               setDuration(1);
                               nextDialog();
                               break;
                       case 6:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_6.png",""));
                               //Step_5: Explains how to move a waypoint
                               setDuration(25);
                               break;
                       case 7:
                               JOptionPane.showMessageDialog(null, "Good Job!");
                               setDuration(1);
                               nextDialog();
                               break;        
                       case 8:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_7.png",""));
                               setDuration(25);
                               break;
                       case 9:
                             JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_8.png",""));
                               setDuration(1);
                               nextDialog();
                               break;
                       case 10:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_9.png",""));
                               setDuration(25);
                               break;
                       case 11:
                               JOptionPane.showMessageDialog(null, "Good Job!");
                               setDuration(2);
                               nextDialog();
                               break;
                       case 12:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_10.png",""));
                               setDuration(25);
                               break;
                       case 13:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_11.png",""));
                               setDuration(1);
                               nextDialog();
                               break;
                       case 14:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_12.png",""));
                               setDuration(25);
                               break;        
                       case 15:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_13.png",""));
                               setDuration(500);
                               break;
                       case 16:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_14.png",""));
                               //Explains the payload window and mission location
                               setDuration(20);
                               break;
                       case 17:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_15.png",""));
                               setDuration(4);
                               nextDialog();
                               break;
                        case 18:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_16.png",""));
                               setDuration(30);
                               break;        
                       case 19:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_17.png",""));
                               setDuration(3);
                               nextDialog();
                               break;        
                       case 20:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_18.png",""));
                               setDuration(50);
                               main.TutorialFinished();
                               break; 
                       case 21:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_19.png",""));
                               setDuration(0);
                               nextDialog();
                               break;
                       case 22:
                               JOptionPane.showMessageDialog(null,
                                       makePicPanel("2_20.png",""));
                               setDuration(0);
                               nextDialog();
                               break;        
                       case 23:
                               //SESSION ENDED
                               main.Restart_Reschu();
                               break;        
                       default:
                               break;
               }
       }

       protected void checkEvent(int type, int vIdx, String target) {
               switch( getState() ) {
                       case 4:
                               checkCorrect(type==MyDB.GP_CHANGE_END_ASSIGNED && vIdx==1 && target.equals("D"));
                               break;
                       case 6:
                               checkCorrect(type==MyDB.GP_CHANGE_END_ASSIGNED && vIdx==1 && target.equals("E"));
                               break;        
                       case 8:
                               checkCorrect(type==MyDB.WP_ADD_END && vIdx == 1);
                               break;
                       case 10:
                               checkCorrect(type==MyDB.WP_MOVE_END && vIdx ==1);
                               break;
                       case 12:
                               checkCorrect(type==MyDB.WP_ADD_END && vIdx ==1);
                               break;
                       case 14:
                               checkCorrect(type==MyDB.WP_DELETE_END && vIdx == 1);
                               break;
                       case 15:
                    	   	   checkCorrect(type==MyDB.VEHICLE_ARRIVES_TO_TARGET && vIdx == 1);
                    	   	   break;
                       case 16:
                               checkCorrect(type==MyDB.PAYLOAD_ENGAGED && vIdx == 1);
                               break;
                       case 18:
                               checkCorrect( (type==MyDB.PAYLOAD_FINISHED_CORRECT || type==MyDB.PAYLOAD_FINISHED_INCORRECT) && vIdx ==1);
                               break;
                       case 20:
                               checkCorrect( (type==MyDB.PAYLOAD_FINISHED_CORRECT || type==MyDB.PAYLOAD_FINISHED_INCORRECT) && vIdx ==2);
                               break;        
                       default:
                               break;
               }
       }
}
