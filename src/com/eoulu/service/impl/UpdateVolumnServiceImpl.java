package com.eoulu.service.impl;

import java.sql.Connection;

import com.eoulu.dao.VolumnDao;
import com.eoulu.service.UpdateVolumeService;
import com.eoulu.util.DBUtil;

public class UpdateVolumnServiceImpl implements UpdateVolumeService{

	@Override
	public void updateVolume() {
		DBUtil dbUtil = new DBUtil();
		Connection connection = dbUtil.getConnection();
		new VolumnDao().updateVolumn(connection);
		dbUtil.closed();	
	}

}
