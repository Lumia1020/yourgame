package com.lhq.service;

import com.lhq.core.Page;
import com.lhq.po.Grid;

public interface IGridService {

	Page getGridList(Page gridPage);

	boolean addGrid(Grid grid);

	boolean deleteGrid(Grid grid);

	boolean updateGrid(Grid grid);

}
