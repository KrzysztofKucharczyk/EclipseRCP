package com.starterkit.library.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.starterkit.library.dialogs.BookAddDialog;
import com.starterkit.library.dialogs.BookDetailsDialog;
import com.starterkit.library.dialogs.BookEditDialog;
import com.starterkit.library.dialogs.BookRemoveConfirmatorDialog;
import com.starterkit.library.models.BookModel;
import com.starterkit.library.models.BookModelProvider;

import swing2swt.layout.BoxLayout;

public class BookListViewPart extends ViewPart {
	private TableViewer tableViewer;
	private Text titleSearchTerm;
	private Text authorsSearchTerm;
	private Composite composite;
	private Composite composite_1;
	private GridData gd_composite;
	private GridData gd_composite_1;
	private GridData gd_text;
	private GridData gridData;
	private Label lblNewLabel_2;
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private Button btnFilter;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;

	public BookListViewPart() {

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		composite = new Composite(parent, SWT.NONE);
		
				lblNewLabel_2 = new Label(composite, SWT.NONE);
				lblNewLabel_2.setText("Search panel");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		initializeSearchComposite();

		btnFilter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.getDisplay().asyncExec(new Runnable() {

					@Override
					public void run() {
						toggleButtons(true);
						buttonFilterAction();
					}
				});
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		new Label(parent, SWT.NONE);

		tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, tableViewer);
		Table table = tableViewer.getTable();
		GridData gd_table = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_table.widthHint = 525;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(BookModelProvider.INSTANCE.getBooks());
		getSite().setSelectionProvider(tableViewer);

		composite_1 = new Composite(parent, SWT.NONE);
		initializeOperationComposite();

		btnAdd.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					BookAddDialog bookAddView = new BookAddDialog(null, tableViewer);
					bookAddView.open();
					break;
				}
			}
		});

		btnEdit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
					BookEditDialog bookEditView = new BookEditDialog(null, (BookModel) selection.getFirstElement(), tableViewer);
					bookEditView.open();
					break;
				}
			}
		});

		btnDelete.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();

					BookRemoveConfirmatorDialog bookRemoveConfirmator = new BookRemoveConfirmatorDialog(null,
							(BookModel) selection.getFirstElement(), tableViewer);
					bookRemoveConfirmator.open();
					toggleButtons(true);
					break;
				}
			}

		});
		new Label(parent, SWT.NONE);

		toggleButtons(true);
		gridData = new GridData();
		initializeTableGird();

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				if (selection.isEmpty())
					return;
				BookDetailsDialog bookDetailsDialog = new BookDetailsDialog(null,
						(BookModel) selection.getFirstElement());
				bookDetailsDialog.open();
			}
		});

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				toggleButtons(false);
				IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				if (selection.isEmpty())
					return;

			}
		});
	}

	private void toggleButtons(boolean isNoRowSelected) {
		if (isNoRowSelected) {
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		} else {
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
		}
	}

	private void initializeSearchComposite() {
		gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite.widthHint = 548;
		gd_composite.heightHint = 64;
		composite.setLayoutData(gd_composite);
		composite.setLayout(new GridLayout(5, false));
		new Label(composite, SWT.NONE);
		btnFilter = new Button(composite, SWT.NONE);
		btnFilter.setText("Filter");
		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Title:");

		titleSearchTerm = new Text(composite, SWT.BORDER);
		gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 64;
		titleSearchTerm.setLayoutData(gd_text);

		lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Authors:");

		authorsSearchTerm = new Text(composite, SWT.BORDER);
		authorsSearchTerm.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	private void initializeOperationComposite() {
		composite_1.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.heightHint = 104;
		gd_composite_1.widthHint = 528;
		composite_1.setLayoutData(gd_composite_1);

		btnAdd = new Button(composite_1, SWT.NONE);
		btnAdd.setText("Add");

		btnEdit = new Button(composite_1, SWT.NONE);
		btnEdit.setText("Edit");

		btnDelete = new Button(composite_1, SWT.NONE);
		btnDelete.setText("Delete");
	}

	private void initializeTableGird() {
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL_HORIZONTAL;
		// tableViewer.getControl().setLayoutData(gridData);
	}

	private void buttonFilterAction() {
		tableViewer
				.setInput(BookModelProvider.INSTANCE.getBooks(titleSearchTerm.getText(), authorsSearchTerm.getText()));
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Title", "Authors", "Status", "Genre", "Year" };
		int[] bounds = { 200, 200, 100, 200, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getTitle();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getAuthors();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getStatus();
			}
		});

		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getGenre();
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				BookModel book = (BookModel) element;
				return book.getYear();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public void setFocus() {

	}

}
