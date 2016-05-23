package com.starterkit.library.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

import com.starterkit.library.dialogs.DeleteBookChooserDialog;

public class DeleteBookHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		DeleteBookChooserDialog dbcd = new DeleteBookChooserDialog(null);
		dbcd.open();
		return null;
	}

}
