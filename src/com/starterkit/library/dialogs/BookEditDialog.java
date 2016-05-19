package com.starterkit.library.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.starterkit.library.models.BookModel;
import com.starterkit.library.models.BookModelProvider;

public class BookEditDialog extends TitleAreaDialog {

	private BookModel bookModel;
	private Text addTitleText;
	private Text addAuthorsText;
	private Text addStatusText;
	private Text addGenreText;
	private Text addYearText;
	private TableViewer tableViewer;
	
	public BookEditDialog(Shell parentShell, BookModel bookModel, TableViewer tableViewer) {
		super(parentShell);
		this.bookModel = bookModel;
		this.tableViewer = tableViewer;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Edit book");

		addTitleText.setText(bookModel.getTitle());
		addAuthorsText.setText(bookModel.getAuthors());
		addStatusText.setText(bookModel.getStatus());
		addGenreText.setText(bookModel.getGenre());
		addYearText.setText(bookModel.getYear());
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Provide data about edited book");
		parent.setToolTipText("Book details");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label lbtTitle = new Label(container, SWT.NONE);
		lbtTitle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbtTitle.setText("Title:");

		addTitleText = new Text(container, SWT.BORDER);
		addTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lbtAuthors = new Label(container, SWT.NONE);
		lbtAuthors.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbtAuthors.setText("Authors:");

		addAuthorsText = new Text(container, SWT.BORDER);
		addAuthorsText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lbtStatus = new Label(container, SWT.NONE);
		lbtStatus.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbtStatus.setText("Title:");

		addStatusText = new Text(container, SWT.BORDER);
		addStatusText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lbtGenre = new Label(container, SWT.NONE);
		lbtGenre.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbtGenre.setText("Genre:");

		addGenreText = new Text(container, SWT.BORDER);
		addGenreText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lbtYear = new Label(container, SWT.NONE);
		lbtYear.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbtYear.setText("Year:");

		addYearText = new Text(container, SWT.BORDER);
		addYearText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return area;
	}

	@Override
	protected void okPressed() {
		BookModelProvider.INSTANCE.updateBook(new BookModel(bookModel.getId(), addTitleText.getText(), addAuthorsText.getText(),
				addStatusText.getText(), addGenreText.getText(), addYearText.getText()));
		tableViewer.setInput(BookModelProvider.INSTANCE.getBooks());
		super.okPressed();
	}

}
