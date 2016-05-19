package com.starterkit.library.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import com.starterkit.library.dialogs.BookAddDialog;

public class AddBookHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		BookAddDialog bookAddDialog = new BookAddDialog(null, null);
		bookAddDialog.open();
		return null;
	}

}
