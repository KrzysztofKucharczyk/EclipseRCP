package com.starterkit.library.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

public class MyDialog extends TitleAreaDialog {

	public MyDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void create()  {
		super.create();
		setTitle("Eloszki");
		setMessage("Myryryr", IMessageProvider.INFORMATION);
	}

}
