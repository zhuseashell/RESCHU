package reschu.game.model;

import reschu.constants.MyGame;
import reschu.constants.MySize;
import reschu.constants.MyTargetBase;
import reschu.game.controller.GUI_Listener;
import reschu.game.controller.Reschu;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Map {
	private Game g;
	private GUI_Listener lsnr;
	private int[][] mapArray;
	private LinkedList<Target> listAssignedTarget = new LinkedList<Target>();
	private LinkedList<Target> listUnassignedTarget = new LinkedList<Target>();
	private LinkedList<int[]> listHazard = new LinkedList<int[]>();
	private int[] suggestedPoint = new int[2];
	private Target suggestedTarget = new Target();
	private int[] preSuggestedPoint = new int[2];
	private Target preSuggestedTarget = new Target();

	Map() {}
	Map(int width, int height, Game g, GUI_Listener l) {
		mapArray = new int[width][height];
		this.g = g;
		lsnr = l;
	}
	
	public List<Target> getListAssignedTarget() {
		return listAssignedTarget;
	}
	public List<Target> getListUnassignedTarget() {
		return listUnassignedTarget;
	}
	public List<int[]> getListHazard() {
		return listHazard;
	}
	public int[] getSuggestedPoint(){
		return suggestedPoint;
	}
	public Target getSuggestedTarget() {
		return suggestedTarget;
	}
	public int[] getPreSuggestedPoint() {
		return preSuggestedPoint;
	}
	public Target getPreSuggestedTarget() {
		return preSuggestedTarget;
	}
	public int getTargetSize(String target_type) {
		int cnt = 0;
		for (int i = 0; i < listAssignedTarget.size(); i++)
			if (listAssignedTarget.get(i).getMission() == target_type)
				cnt++;
		for (int i = 0; i < listUnassignedTarget.size(); i++)
			if (listUnassignedTarget.get(i).getMission() == target_type)
				cnt++;
		return cnt;
	}
	public void setSuggestedPoint(int[] pos){
        suggestedPoint[0] = pos[0];
        suggestedPoint[1] = pos[1];
	}
	public void setSuggestedTarget(Target target) {
	    suggestedTarget = target;
	}
	public void setPreSuggestedPoint(int[] pos) {
		preSuggestedPoint[0] = pos[0];
		preSuggestedPoint[1] = pos[1];
	}
	public void setPreSuggestedTarget(Target target) {
		preSuggestedTarget = target;
	}
	public synchronized void setCellType(int x, int y, int type) {
		mapArray[x][y] = type;
	}
	public synchronized int getCellType(int x, int y) {
		return mapArray[x][y];
	}
	
	public synchronized void addTarget(Target t) {
		if (g.isRunning())
			lsnr.EVT_Target_Generated(t.getName(), t.getPos(), t.isVisible());
		listUnassignedTarget.addLast(t);
	}
	
	public synchronized void addHazard(int x, int y, int size) {
		if (g.isRunning())
			lsnr.EVT_HazardArea_Generated(new int[] { x, y });
		listHazard.addLast(new int[] { x, y, size });
	}
	
	public synchronized void assignTarget(int[] coordinate) {
		for (int i = 0; i < listUnassignedTarget.size(); i++) {
			if (boundaryCheck(coordinate[0], coordinate[1], listUnassignedTarget.get(i).getPos())) {
				listAssignedTarget.add(listUnassignedTarget.get(i));
				listUnassignedTarget.remove(i);
			}
		}
	}
	
	public synchronized void unassignTarget(int[] coordinate) {
		for (int i = 0; i < listAssignedTarget.size(); i++) {
			if (boundaryCheck(coordinate[0], coordinate[1], listAssignedTarget.get(i).getPos())) {
				listUnassignedTarget.add(listAssignedTarget.get(i));
				listAssignedTarget.remove(i);
			}
		}
	}
	
	public synchronized void delHazard(int idx) {
		lsnr.EVT_HazardArea_Disappeared(new int[] { listHazard.get(idx)[0], listHazard.get(idx)[1] });
		listHazard.remove(idx);
	}
	
	public synchronized boolean isTarget(int x, int y) {
		if (isAssignedTarget(x, y) || isUnassignedTarget(x, y))
			return true;
		return false;
	}
	
	public synchronized boolean isAssignedTarget(int x, int y) {
		for (int i = 0; i < listAssignedTarget.size(); i++) {
			if (listAssignedTarget.get(i).getPos()[0] == x && listAssignedTarget.get(i).getPos()[1] == y)
				return true;
		}
		return false;
	}
	
	public synchronized boolean isUnassignedTarget(int x, int y) {
		for (int i = 0; i < listUnassignedTarget.size(); i++) {
			if (listUnassignedTarget.get(i).getPos()[0] == x && listUnassignedTarget.get(i).getPos()[1] == y)
				return true;
		}
		return false;
	}
	
	public synchronized boolean isHazard(int x, int y) {
		for (int i = 0; i < listHazard.size(); i++) {
			if (listHazard.get(i)[0] == x && listHazard.get(i)[1] == y)
				return true;
		}
		return false;
	}
	
	private boolean boundaryCheck(int x, int y, int[] target_pos) {
		int w = Math.round(MySize.SIZE_TARGET_PXL / MySize.SIZE_CELL / 2);

		for (int i = -w; i < w; i++)
			for (int j = -w; j < w; j++)
				if (x + i == target_pos[0] && y + j == target_pos[1])
					return true;
		return false;
	}
	
	public void setHazardArea(Random rnd) {
		int nHazard = (Reschu.tutorial()) ? MyGame.nHAZARD_AREA_TUTORIAL : MyGame.nHAZARD_AREA;
		int nHazardNeed = nHazard - getListHazard().size();
		int x, y;

		for (int i = 0; i < nHazardNeed; i++) {
			while (!chkOkayToAdd((x = rnd.nextInt(MySize.width)), (y = rnd.nextInt(MySize.height)))) {
			}
			addHazard(x, y, rnd.nextInt(5));
		}
	}
	
	private boolean chkOkayToAdd(int x, int y) {
		final int LIMIT_DISTANCE = 40;
		final boolean DEBUG = false;

		for (int i = 0; i < listAssignedTarget.size(); i++) {
			Target t = listAssignedTarget.get(i);
			if (Game.getDistance(t.getPos()[0], t.getPos()[1], x, y) < LIMIT_DISTANCE) {
				int x1 = t.getPos()[0], y1 = t.getPos()[1], x2 = x, y2 = y;
				double d = Game.getDistance(t.getPos()[0], t.getPos()[1], x, y);
				if (DEBUG)
					System.err.println("LAT (" + x1 + "," + y1 + ") with randomly generated (" + x2 + "," + y2
							+ "), d=(" + d + ")");
				return false;
			}
		}
		for (int i = 0; i < listUnassignedTarget.size(); i++) {
			Target t = listUnassignedTarget.get(i);
			if (Game.getDistance(t.getPos()[0], t.getPos()[1], x, y) < LIMIT_DISTANCE) {
				int x1 = t.getPos()[0], y1 = t.getPos()[1], x2 = x, y2 = y;
				double d = Game.getDistance(t.getPos()[0], t.getPos()[1], x, y);
				if (DEBUG)
					System.err.println("LUT(" + x1 + "," + y1 + ") with randomly generated (" + x2 + "," + y2 + "), d=("
							+ d + ")");
				return false;
			}
		}
		for (int i = 0; i < listHazard.size(); i++) {
			if (Game.getDistance(x, y, listHazard.get(i)[0], listHazard.get(i)[1]) < LIMIT_DISTANCE) {
				int x1 = listHazard.get(i)[0], y1 = listHazard.get(i)[1], x2 = x, y2 = y;
				double d = Game.getDistance(x, y, listHazard.get(i)[0], listHazard.get(i)[1]);
				if (DEBUG)
					System.err.println("LHA(" + x1 + "," + y1 + ") with randomly generated (" + x2 + "," + y2 + "), d=("
							+ d + ")");
				return false;
			}
		}
		for (int i = 0; i < g.getVehicleList().size(); i++) {
			Vehicle v = g.getVehicleList().getVehicle(i);
			if (Game.getDistance(v.getGroundTruthX(), v.getGroundTruthY(), x, y) < LIMIT_DISTANCE) {
				int x1 = v.getGroundTruthX(), y1 = v.getGroundTruthY(), x2 = x, y2 = y;
				double d = Game.getDistance(v.getGroundTruthX(), v.getGroundTruthY(), x, y);
				if (DEBUG)
					System.err.println("LVH(" + x1 + "," + y1 + ") with randomly generated (" + x2 + "," + y2 + "), d=("
							+ d + ")");
				return false;
			}
		}
		return true;
	}

	public void delHazardArea(Random rnd, int n) {
		for (int i = 0; i < n; i++)
			delHazard(rnd.nextInt(getListHazard().size()));
	}

	public void setTargetArea(Random rnd) throws UserDefinedException {
		int nTargetAreaLand = (Reschu.tutorial()) ? MyGame.nTARGET_AREA_LAND_TUTORIAL : MyGame.nTARGET_AREA_LAND;
		int nLandTargetNeed = nTargetAreaLand - getTargetSize("LAND");
		int nShoreTargetNeed = MyGame.nTARGET_AREA_SHORE - getTargetSize("SHORE");
		int LowHighTaskTarget = (Reschu.if_section_1()) ? MyGame.nTARGET_AREA_COMM : MyGame.nTARGET_AREA_MORE;
		int nCommTargetNeed = LowHighTaskTarget - getTargetSize("COMM");

		int cnt = 0;
		final int limit = 100000;

		for (int i = 0; i < nLandTargetNeed; i++) {
			int x, y;
			do {
				x = rnd.nextInt(MySize.width);
				y = rnd.nextInt(MySize.height);
				if (++cnt >= limit) {
					throw new UserDefinedException("setTargetArea(land) try limit exceed");
				}
			} while (!(getCellType(x, y) != MyGame.SEA && chkOkayToAdd(x, y)));
			Target t = new Target(g.getEmptyTargetName(), chkTargetOffset(x, y), "LAND", "UAV",
					g.getTargetVisibility());
			addTarget(t);
		}
		for (int i = 0; i < nShoreTargetNeed; i++) {
			int x, y;
			do {
				x = rnd.nextInt(MySize.width);
				y = rnd.nextInt(MySize.height);
				if (++cnt >= limit) {
					throw new UserDefinedException("setTargetArea(shore) try limit exceed");
				}
			} while (!(getCellType(x, y) == MyGame.SEASHORE && chkOkayToAdd(x, y)));
			Target t = new Target(g.getEmptyTargetName(), chkTargetOffset(x, y), "SHORE", "UUV",
					g.getTargetVisibility());
			addTarget(t);
		}
		for (int i = 0; i < nCommTargetNeed; i++) {
			int x, y;
			do {
				x = rnd.nextInt(MySize.width);
				y = rnd.nextInt(MySize.height);
				if (++cnt >= limit) {
					throw new UserDefinedException("setTargetArea(comm) try limit exceed");
				}
			} while (!chkOkayToAdd(x, y));
			Target t = new Target(g.getEmptyTargetName(), chkTargetOffset(x, y), "COMM", "UAV",
					g.getTargetVisibility());
			addTarget(t);
		}
	}
	
	// set target area based on pre-defined database
	public void setTargetArea_DataBase(Random rnd) throws UserDefinedException {
		int nTotalTarget = g.getTotalTargetNumber();
		int nTotalTargetNeed = nTotalTarget - getTargetSize("LAND");
		int count = 0;

		for (int i=0; i<nTotalTargetNeed; i++) {
			int[] temp_t;
			do {
				MyTargetBase.addTargetIndex();
				count ++;
				if(MyTargetBase.getTargetIndex() >= MyTargetBase.getTargetBaseSize()) {
					MyTargetBase.resetTargetIndex();
					count = 0;
				}
				// temp_t = MyTargetBase.getTargetInfo(MyTargetBase.getTargetIndex());
				int index = rnd.nextInt(MyTargetBase.getTargetBaseSize());
				temp_t = MyTargetBase.getTargetInfo(index);
				
				// System.out.println("INDEX = "+index);
				// System.out.println("TARGET = "+temp_t[0]+" "+temp_t[1]+" "+count+" "+MyTargetBase.getTargetIndex()+" "+MyTargetBase.getTargetBaseSize());
				
				if (count >= MyTargetBase.getTargetBaseSize()) {
					throw new UserDefinedException("Target index exceeds limit");
				}
			} while (!(getCellType(temp_t[0], temp_t[1])!=MyGame.SEA && chkOkayToAdd(temp_t[0], temp_t[1])));
			
			int[] target_ans = new int[3];
			for(int j=3; j<temp_t.length; j++) target_ans[j-3] = temp_t[j];
			Target t = new Target(g.getEmptyTargetName(), chkTargetOffset(temp_t[0], temp_t[1]), "LAND", "UAV",
					g.getTargetVisibility(), temp_t[2], target_ans);
			
			// System.out.println("TARGET = "+t.getPos()[0]+" "+t.getPos()[1]);
			addTarget(t);
		}
	}

	private int[] chkTargetOffset(int x, int y) {
		int offset = 10;
		if (x < offset)
			x += offset;
		if (y < offset)
			y += offset;
		if (x > MySize.width - offset)
			x -= offset;
		if (y > MySize.height - offset)
			y -= offset;
		return new int[] { x, y };
	}

	public int getAvailableTarget() {
		int cnt = 0;
		for (int i = 0; i < getListUnassignedTarget().size(); i++) {
			if (getListUnassignedTarget().get(i).isVisible())
				cnt++;
		}
		return cnt;
	}

	public void garbageTargetCollect() {
		for (int i=0; i<getListAssignedTarget().size(); i++) {
			if (getListAssignedTarget().get(i).isDone()) {
				Target t = getListAssignedTarget().get(i);
				lsnr.EVT_Target_Disappeared(t.getName(), t.getPos());
				g.setTargetUsed(t.getName(), false);
				getListAssignedTarget().remove(i);
			}
		}
	}

	// THIS IS TEMPORARY
	private boolean chkOkayToAdd_TEMPORARY_FOR_TUTORIAL_BY_CARL(int x, int y) {
		for (int j = 0; j < listAssignedTarget.size(); j++) {
			Target t = listAssignedTarget.get(j);
			if (Game.getDistance(t.getPos()[0], t.getPos()[1], x, y) < MySize.SIZE_HAZARD_3_PXL)
				// System.err.println("Target[" + t.getName() +"] d=" +
				// Game.getDistance(t.getPos()[0], t.getPos()[1], x, y ));
				return false;
		}
		for (int i = 0; i < listUnassignedTarget.size(); i++) {
			Target t = listUnassignedTarget.get(i);
			if (Game.getDistance(t.getPos()[0], t.getPos()[1], x, y) < MySize.SIZE_HAZARD_3_PXL)
				// System.err.println("Target[" + t.getName() +"] d=" +
				// Game.getDistance(t.getPos()[0], t.getPos()[1], x, y ));
				return false;
		}
		for (int i = 0; i < listHazard.size(); i++) {
			if (Game.getDistance(x, y, listHazard.get(i)[0], listHazard.get(i)[1]) < MySize.SIZE_HAZARD_3_PXL)
				return false;
		}
		for (int i = 0; i < g.getVehicleList().size(); i++) {
			Vehicle v = g.getVehicleList().getVehicle(i);
			if (Game.getDistance(v.getGroundTruthX(), v.getGroundTruthY(), x, y) < MySize.SIZE_HAZARD_3_PXL)
				return false;
		}
		return true;
	}

	// THIS IS TEMPORARY
	public void setTargetArea_TEMPORARY_FOR_TUTORIAL_BY_CARL(Random rnd) {
		int nTargetAreaLand = (Reschu.tutorial()) ? MyGame.nTARGET_AREA_LAND_TUTORIAL : MyGame.nTARGET_AREA_LAND;
		int nLandTargetNeed = nTargetAreaLand - getTargetSize("LAND");
		int nShoreTargetNeed = MyGame.nTARGET_AREA_SHORE - getTargetSize("SHORE");
		int nCommTargetNeed = MyGame.nTARGET_AREA_COMM - getTargetSize("COMM");

		// System.out.println("Shore "+nShoreTargetNeed);

		for (int i = 0; i < nLandTargetNeed; i++) {
			int x, y;
			do {
				// System.out.println("LAND");
				x = rnd.nextInt(MySize.width);
				y = rnd.nextInt(MySize.height);
			} while (getCellType(x, y) == MyGame.SEA || !chkOkayToAdd_TEMPORARY_FOR_TUTORIAL_BY_CARL(x, y));
			Target t = new Target(g.getEmptyTargetName(), chkTargetOffset(x, y), "LAND", "UAV",
					g.getTargetVisibility());
			addTarget(t);
		}
		for (int i = 0; i < nShoreTargetNeed; i++) {
			int x, y;
			do {
				// System.out.println("SHORE");
				x = rnd.nextInt(MySize.width);
				y = rnd.nextInt(MySize.height);
				// if (getCellType(y,x)!=MyGame.SEASHORE)
				// System.out.println("LAND TYPE PROB");
				// if (!chkOkayToAdd(x,y)) System.out.println("GROUND TAKEN");
			} while (getCellType(x, y) != MyGame.SEASHORE || !chkOkayToAdd_TEMPORARY_FOR_TUTORIAL_BY_CARL(x, y));
			Target t = new Target(g.getEmptyTargetName(), chkTargetOffset(x, y), "SHORE", "UUV",
					g.getTargetVisibility());
			addTarget(t);
		}
		for (int i = 0; i < nCommTargetNeed; i++) {
			int x, y;
			do {
				x = rnd.nextInt(MySize.width);
				y = rnd.nextInt(MySize.height);
			} while (!chkOkayToAdd_TEMPORARY_FOR_TUTORIAL_BY_CARL(x, y));
			Target t = new Target(g.getEmptyTargetName(), chkTargetOffset(x, y), "COMM", "UAV",
					g.getTargetVisibility());
			addTarget(t);
		}
	}
	
	public boolean isAssignedTarget(Target target) {
		for(int i=0; i<listAssignedTarget.size(); i++) {
			if(listAssignedTarget.get(i) == target) return true;
		}
		return false;
	}
	
	public boolean isUnassignedTarget(Target target) {
		for(int i=0; i<listUnassignedTarget.size(); i++) {
			if(listUnassignedTarget.get(i) == target) return true;
		}
		return false;
	}
}