package com.example.gwt.gwtApp.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.example.gwt.gwtApp.shared.FieldValidator;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtApp implements EntryPoint {

    final Button confirmButton = new Button("Confirm");
    final TextBox nameField = new TextBox();
    final Label errorLabel = new Label();
    final Label helloLabel = new Label();
    final TextBox secondNameField = new TextBox();

    VerticalPanel dialogVPanel = new VerticalPanel();
    final DialogBox dialogBox = new DialogBox();
    final HTML serverResponseHtml = new HTML();
    final Label sendToServerLabel = new Label();
    final Button closeButton = new Button("Close");

    private final GwtAppServiceIntfAsync gwtAppService = GWT.create(GwtAppServiceIntf.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        helloLabel.setText("GwtApp Application hello world");

        final Label usernameLabel = new Label();
        usernameLabel.setText("Username: ");
        /*Связываем id='' на html странице с компонентами */
        RootPanel.get("helloId").add(helloLabel);


        RootPanel.get("usernameLabelId").add(usernameLabel);
        RootPanel.get("usernameId").add(nameField);
        RootPanel.get("secondUsernameId").add(secondNameField);

        RootPanel.get("confirmButtonId").add(confirmButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        // Create the popup dialog box
        dialogBox.setText("Remote procedure call from server");
        dialogBox.setAnimationEnabled(true);

        closeButton.getElement().setId("closeButtonId");

        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Отправленные поля на сервер:</b>"));
        dialogVPanel.add(sendToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Ответ сервера:</b>"));
        dialogVPanel.add(serverResponseHtml);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        //обработчик для клика по кнопке 'Confirm'
        confirmButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                confirmButton.setEnabled(false);
                sendInfoToServer();
            }
        });

        //обработчик по нажатию enter в текстовом поле
        nameField.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendInfoToServer();
                }
            }
        });

        secondNameField.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    sendInfoToServer();
                }
            }
        });
        //обработчик по клику на кнопку 'Close' в диалоговом окне
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                confirmButton.setEnabled(true);
                confirmButton.setFocus(true);
            }
        });

    }

    private void sendInfoToServer() {
        //validate input text
        errorLabel.setText("");
        String nameToServer = nameField.getText() + " " + secondNameField.getText();
        if (!FieldValidator.isValidData(nameToServer)) { //отобразить ошибку на html странице
            errorLabel.setText("Имя должно содержать больше трех символов");
            return;
        }
        sendToServerLabel.setText(nameToServer);
        confirmButton.setEnabled(false);
        serverResponseHtml.setText("");
        gwtAppService.gwtAppCallServer(nameToServer, new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                dialogBox.setText("Remote Procedure Call - Failure");
                serverResponseHtml.addStyleName("serverResponseLabelError");
                serverResponseHtml.setHTML("ERROR ON SERVER");
                dialogBox.center();
                closeButton.setFocus(true);
            }

            public void onSuccess(String result) {
                dialogBox.setText("Remote Procedure Call");
                serverResponseHtml.removeStyleName("serverResponseLabelError");
                serverResponseHtml.setHTML(result);
                dialogBox.center();
                closeButton.setFocus(true);
            }
        });

    }

}