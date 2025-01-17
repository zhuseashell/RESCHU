package reschu.constants;

public class MyDB {
	final public static int INVOKER_SYSTEM				= 0;
	final public static int INVOKER_USER				= 1;


	final public static int WP_ADD_START				= 11;
	final public static int WP_ADD_END					= 12;
	final public static int WP_MOVE_START				= 13;
	final public static int WP_MOVE_END					= 14;
	final public static int WP_DELETE_START				= 15;
	final public static int WP_DELETE_END				= 16;
	final public static int WP_ADD_CANCEL				= 17;
	final public static int WP_ADD_PREV					= 18;
	final public static int WP_ADD_NEXT					= 19;
	
	final public static int GP_SET_BY_SYSTEM			= 21;
	final public static int GP_SET_START				= 22;
	final public static int GP_SET_END_ASSIGNED			= 23;
	final public static int GP_SET_END_UNASSIGNED		= 24;
	final public static int GP_CHANGE_START				= 25;
	final public static int GP_CHANGE_END_ASSIGNED		= 26;
	final public static int GP_CHANGE_END_UNASSIGNED	= 27;
	final public static int GP_SET_CANCEL				= 28;
	
	final public static int TARGET_GENERATED			= 31;
	final public static int TARGET_BECAME_VISIBLE		= 32;
	final public static int TARGET_DISAPPEARED			= 33;
	
	final public static int PAYLOAD_ENGAGED_AND_FINISHED= 41;
	final public static int PAYLOAD_FINISHED_CORRECT	= 42;
	final public static int PAYLOAD_FINISHED_INCORRECT	= 43;
	final public static int PAYLOAD_ENGAGED_FROM_PNLMAP	= 44;
	final public static int PAYLOAD_ENGAGED_FROM_PNLCOMPACT	= 45;
	final public static int PAYLOAD_ENGAGED_FROM_PNLUAV	= 46;
	
	final public static int VEHICLE_DAMAGED				= 51;
	final public static int VEHICLE_SPEED_DECREASED		= 52;
	final public static int VEHICLE_ARRIVES_TO_TARGET	= 53;
	final public static int HACKED_UAV_ARRIVES_TARGET	= 54;
	final public static int VEHICLE_INTERSECT_HAZARDAREA= 55;
	final public static int VEHICLE_ESCAPE_HAZARDAREA	= 56;
	
	final public static int HAZARDAREA_GENERATED		= 61;
	final public static int HAZARDAREA_DISAPPEARED		= 62; 
		
	final public static int SYSTEM_GAME_START			= 141;
	final public static int SYSTEM_GAME_END				= 142;
	
	final public static int YVES_VEHICLE_SELECT_TAB		= 101;
	final public static int YVES_VEHICLE_DESELECT_TAB	= 102;
	final public static int YVES_VEHICLE_SELECT_MAP_LBTN = 103;
	final public static int YVES_VEHICLE_SELECT_MAP_RBTN = 104;
	final public static int YVES_VEHICLE_DESELECT_MAP_LBTN= 105;

	final public static int STATE_NOT_LOGGED_IN_YET		= 0;
	final public static int STATE_WEB_LOG_IN			= 1;
	final public static int STATE_GAME_START			= 2;
	final public static int STATE_GAME_FINISH			= 3;
	
	final public static int HACK_LAUNCHED 				= 111;
	final public static int HACK_LAUNCHED_FAKE			= 112;
	
	final public static int HACK_NOTIFICATION_LAUNCHED	= 121;
	final public static int HACK_NOTIFICATION_IGNORED	= 122;
	final public static int HACK_NOTIFICATION_INVESTIGATED = 123;
	final public static int HACK_NOTIFICATION_MISSED 	= 124;
	
	final public static int HOME_FROM_COMPACT			= 70;
	final public static int HOME_FROM_UAV_PANEL			= 71;
	final public static int HOME_FROM_RIGHT_CLICK		= 72;
	final public static int HOME_FROM_COMPACT_YES		= 73;
	final public static int HOME_FROM_COMPACT_NO		= 74;
	final public static int HOME_FROM_UAV_PANEL_YES		= 75;
	final public static int HOME_FROM_UAV_PANEL_NO		= 76;
	final public static int HOME_FROM_RIGHT_CLICK_YES	= 77;
	final public static int HOME_FROM_RIGHT_CLICK_NO	= 78;
	final public static int UAV_NOT_HACKED_DECIDED		= 79;
	final public static int UAV_HACKED_DECIDED			= 80;
	
	final public static int CORRECT_HACKING_DETECT		= 87;
	final public static int INCORRECT_HACKING_DETECT	= 88;
	
	final public static int VEHICLE_ADDED				= 81;
	final public static int VEHICLE_DELETED				= 82;
	final public static int NEW_TARGET_ASSIGNED			= 83;
	
	final public static int GENERATE_GHOST_MISSION		= 85;
	final public static int ATTACKED_UAV_DISAPPEAR		= 86;
	
	final public static int ZOOM_IN						= 90;
	final public static int ZOOM_OUT					= 91;
	final public static int ZOOM_MAX					= 92;
	final public static int ZOOM_MIN					= 93;
	
	final public static int CORRECT_TASK_ANSWER			= 130;
	final public static int INCORRECT_TASK_ANSWER		= 131;

    final public static int ACCEPT_SUGGESTION_WAYPOINT  = 150;
    final public static int REJECT_SUGGESTION_WAYPOINT  = 151;

    final public static int INIT_SUGGESTION_WAYPOINT	= 154;
	final public static int CHANGE_SUGGESTION_WAYPOINT  = 155;
	final public static int UPDATE_SUGGESTION_WAYPOINT  = 156;

	final public static int ACCEPT_SUGGESTION_TARGET    = 152;
	final public static int REJECT_SUGGESTION_TARGET    = 153;

	final public static int CHANGE_SUGGESTION_TARGET	= 157;
	final public static int INIT_SUGGESTION_TARGET		= 158;
	final public static int UPDATE_SUGGESTION_TARGET	= 159;

}