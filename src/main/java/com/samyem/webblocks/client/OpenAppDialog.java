package com.samyem.webblocks.client;

import static com.samyem.webblocks.client.controller.Callback.callback;
import static com.samyem.webblocks.client.controller.WebBlocksClient.webBlocksService;

import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.shared.Application;

public class OpenAppDialog extends Composite {
	private static OpenAppDialogUiBinder uiBinder = GWT.create(OpenAppDialogUiBinder.class);

	interface OpenAppDialogUiBinder extends UiBinder<Widget, OpenAppDialog> {
	}

	@UiField
	Button btnOpen, btnCancel;

	@UiField
	FlexTable grid;

	private List<Application> curApps;
	private Application selectedApp;

	private Consumer<Application> dialogConsumer;

	private int prevSelIndex = -1;

	public OpenAppDialog(Consumer<Application> dialogConsumer) {
		initWidget(uiBinder.createAndBindUi(this));

		this.dialogConsumer = dialogConsumer;

		grid.setText(0, 0, "Name");
		RowFormatter rowFormatter = grid.getRowFormatter();
		rowFormatter.addStyleName(0, "tableHeader");

		grid.addClickHandler(event -> {
			Cell cell = grid.getCellForEvent(event);
			if (cell == null) {
				return;
			}
			int index = cell.getRowIndex();
			if (index < 1) {
				return;
			}

			selectedApp = curApps.get(index - 1);
			rowFormatter.setStyleName(index, "selected");

			if (prevSelIndex != index && prevSelIndex > -1) {
				rowFormatter.removeStyleName(prevSelIndex, "selected");
			}
			prevSelIndex = index;
		});

		// load the app metadatas
		webBlocksService.getWebBlocks(callback(apps -> {
			GWT.log(apps.toString());
			curApps = apps;

			for (int i = 0; i < apps.size(); i++) {
				Application application = apps.get(i);
				grid.setText(i + 1, 0, application.getName());
			}
		}));
	}

	@UiHandler("btnOpen")
	void onClick(ClickEvent e) {
		dialogConsumer.accept(selectedApp);
	}

	@UiHandler("btnCancel")
	void onCancel(ClickEvent e) {
		dialogConsumer.accept(null);
	}

	public static void show(Consumer<Application> appConsumer) {
		DialogBox dialog = new DialogBox(true);
		dialog.setText("Open Application");
		dialog.setAnimationEnabled(true);
		dialog.setGlassEnabled(true);
		dialog.setModal(true);
		dialog.center();

		Consumer<Application> dialogConsumer = a -> {
			dialog.hide();
			if (a != null) {

				// load the full app
				webBlocksService.getWebBlock(a.getId(), callback(app -> {
					appConsumer.accept(app);
				}));
			}
		};

		OpenAppDialog content = new OpenAppDialog(dialogConsumer);
		dialog.setWidget(content);

		dialog.show();
	}

}
