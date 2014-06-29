/**
 * Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team
 * http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.core.robots;

import java.util.LinkedList;

import buildcraft.api.core.BlockIndex;
import buildcraft.api.robots.AIRobot;
import buildcraft.api.robots.EntityRobotBase;
import buildcraft.core.utils.IPathFound;
import buildcraft.core.utils.PathFinding;
import buildcraft.core.utils.PathFindingJob;

public class AIRobotSearchBlock extends AIRobot {

	public BlockIndex blockFound;
	private PathFinding blockScanner = null;
	private PathFindingJob blockScannerJob;
	private IPathFound pathFound;

	public AIRobotSearchBlock(EntityRobotBase iRobot, IPathFound iPathFound) {
		super(iRobot, 0);

		pathFound = iPathFound;
	}

	@Override
	public void start() {
		blockScanner = new PathFinding(robot.worldObj, new BlockIndex(robot), pathFound, 64, robot.getAreaToWork());
		blockScannerJob = new PathFindingJob(blockScanner);
		blockScannerJob.start();
	}

	@Override
	public void update() {
		if (blockScannerJob.isDone()) {
			LinkedList<BlockIndex> path = blockScanner.getResult();
			blockFound = path.removeLast();
			startDelegateAI(new AIRobotGotoBlock(robot, path));
		}
	}

	@Override
	public void delegateAIEnded(AIRobot ai) {
		if (ai instanceof AIRobotGotoBlock) {
			terminate();
		}
	}

	@Override
	public void end() {
		if (blockScannerJob != null) {
			blockScannerJob.terminate();
		}
	}
}