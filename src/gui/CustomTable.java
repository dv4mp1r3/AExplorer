/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Dm
 */
public class CustomTable extends JTable 
{ 
	private void applyColumnSettings()
	{
		final int iconColumnWidth = 20;

		this.getColumnModel().getColumn(0).setResizable(false);           
		this.getColumnModel().getColumn(0).setPreferredWidth(iconColumnWidth);
		this.getColumnModel().getColumn(0).setWidth(iconColumnWidth);
		this.getColumnModel().getColumn(0).setMinWidth(iconColumnWidth);
		this.getColumnModel().getColumn(0).setMaxWidth(iconColumnWidth);

		this.getTableHeader().setReorderingAllowed(false);
	}

	//@Override
	public void setModel(TableModel dataModel, TableModelListener l) 
	{      
		int[] list = new int[this.getColumnCount()];
		if (l != null)
		{
			dataModel.addTableModelListener(l);
		}
		if (list.length != 0)
		{
			for (int i = 0; i < getColumnCount(); i++)
				list[i] = this.getColumnModel().getColumn(i).getPreferredWidth();

			super.setModel(dataModel);

			for (int i = 0; i < getColumnCount(); i++)
			   this.getColumnModel().getColumn(i).setPreferredWidth(list[i]);

			applyColumnSettings();
		}
		else
		{
			super.setModel(dataModel);
			if (this.getColumnCount() != 0)
				applyColumnSettings();
		}      
	}

	public int getColumnNumberByName(String columnName)
	{
		int count = getModel().getColumnCount();
		for (int i = 0; i < count; i++)
		{
			if (getModel().getColumnName(i).equals(columnName))
				return i;
		}
		return -1;
	}


}
