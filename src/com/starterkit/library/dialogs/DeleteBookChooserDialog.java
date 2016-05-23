package com.starterkit.library.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.starterkit.library.models.BookModelProvider;

public class DeleteBookChooserDialog extends TitleAreaDialog {

	private Text bookIdText;

	public DeleteBookChooserDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Delete book by id");
		setMessage("Provide id of a book, which should be removed.");
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		parent.setToolTipText("Remove book");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label lbtTitle = new Label(container, SWT.NONE);
		lbtTitle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbtTitle.setText("Id:");

		bookIdText = new Text(container, SWT.BORDER);
		bookIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return area;
	}

	@Override
	protected void okPressed() {

		BookModelProvider.INSTANCE.deleteBook(Long.parseLong(bookIdText.getText()));
		BookModelProvider.INSTANCE.getBooksFromServer();
		super.okPressed();
	}

}
