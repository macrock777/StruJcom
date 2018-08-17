package com.efive.agencyonline.common;

import java.util.Comparator;

public class EfiveListComparator implements Comparator {

	private int fieldIndex;

	private int orderby;

	public EfiveListComparator(int fieldIndex, int orderby) {
		super();
		this.fieldIndex = fieldIndex;
		this.orderby = orderby;
	}

	@Override
	public int compare(Object o1, Object o2) {	
		Object[] obj1 = (Object[]) o1;
		Object[] obj2 = (Object[]) o2;
		if (null == obj1[fieldIndex])
			return 1 * orderby;
		else if (null == obj2[fieldIndex])
			return -1 * orderby;

		return obj1[fieldIndex].toString().compareTo(
				obj2[fieldIndex].toString())
				* orderby;
	}
}
