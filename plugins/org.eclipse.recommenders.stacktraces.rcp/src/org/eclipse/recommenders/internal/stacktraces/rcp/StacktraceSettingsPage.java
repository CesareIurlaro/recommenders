/**
 * Copyright (c) 2014 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 *    Daniel Haftstein - added support for multiple stacktraces
 */
package org.eclipse.recommenders.internal.stacktraces.rcp;

import static org.eclipse.jface.fieldassist.FieldDecorationRegistry.DEC_INFORMATION;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.recommenders.internal.stacktraces.rcp.StacktraceWizard.WizardPreferences;
import org.eclipse.recommenders.internal.stacktraces.rcp.StacktracesRcpPreferences.Mode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.Lists;

class StacktraceSettingsPage extends WizardPage {

    private ComboViewer actionComboViewer;
    private Text emailText;
    private Text nameText;
    private Button anonymizeStacktracesButton;
    private Button clearMessagesButton;
    private WizardPreferences wizardPreferences;

    protected StacktraceSettingsPage(WizardPreferences wizardPreferences) {
        super(StacktraceSettingsPage.class.getName());
        this.wizardPreferences = wizardPreferences;
    }

    @Override
    public void createControl(Composite parent) {
        setTitle("An error has been logged. Help us fixing it.");
        setDescription("Please provide any additional information\nthat may help us to reproduce the problem (optional).");
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());

        GridLayoutFactory layoutFactory = GridLayoutFactory.fillDefaults().numColumns(2);
        GridDataFactory dataFactory = GridDataFactory.fillDefaults().grab(true, false);
        Group personalGroup = new Group(container, SWT.SHADOW_ETCHED_IN | SWT.SHADOW_ETCHED_OUT | SWT.SHADOW_IN
                | SWT.SHADOW_OUT);
        personalGroup.setText("Personal Information");
        layoutFactory.applyTo(personalGroup);
        dataFactory.applyTo(personalGroup);
        FieldDecoration infoDecoration = FieldDecorationRegistry.getDefault().getFieldDecoration(DEC_INFORMATION);
        {
            new Label(personalGroup, SWT.NONE).setText("Name:");
            nameText = new Text(personalGroup, SWT.BORDER);
            nameText.setText(wizardPreferences.name);
            nameText.addModifyListener(new ModifyListener() {
                @Override
                public void modifyText(ModifyEvent event) {
                    wizardPreferences.name = nameText.getText();
                }
            });
            dataFactory.applyTo(nameText);
            ControlDecoration dec = new ControlDecoration(nameText, SWT.TOP | SWT.LEFT);
            dec.setImage(infoDecoration.getImage());
            dec.setDescriptionText("Optional. May be helpful for the team to see who reported the issue.");
        }
        {
            new Label(personalGroup, SWT.NONE).setText("Email:");
            emailText = new Text(personalGroup, SWT.BORDER);
            emailText.setText(wizardPreferences.email);
            emailText.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent event) {
                    wizardPreferences.email = emailText.getText();
                }
            });
            dataFactory.applyTo(emailText);
            ControlDecoration dec = new ControlDecoration(emailText, SWT.TOP | SWT.LEFT);
            dec.setImage(infoDecoration.getImage());
            dec.setDescriptionText("Optional. Your email address allows us to get in touch with you when this issue has been fixed.");
        }
        {
            new Label(personalGroup, SWT.NONE).setText("Action:");
            actionComboViewer = new ComboViewer(personalGroup, SWT.READ_ONLY);
            actionComboViewer.setContentProvider(ArrayContentProvider.getInstance());
            actionComboViewer.setInput(Lists.newArrayList(Mode.class.getEnumConstants()));
            actionComboViewer.setLabelProvider(new LabelProvider() {
                @Override
                public String getText(Object element) {
                    Mode mode = (Mode) element;
                    switch (mode) {
                    case ASK:
                        return "Report now but ask me again next time.";
                    case IGNORE:
                        return "Don't report and never ask me again.";
                    case SILENT:
                        return "I love to help. Send all errors you see to the dev team immediately.";
                    default:
                        return super.getText(element);
                    }
                }
            });
            actionComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    if (!event.getSelection().isEmpty()) {
                        IStructuredSelection selection = (IStructuredSelection) actionComboViewer.getSelection();
                        Mode mode = (Mode) selection.getFirstElement();
                        if (mode != null) {
                            wizardPreferences.mode = mode;
                        }
                    }

                }
            });
            actionComboViewer.setSelection(new StructuredSelection(wizardPreferences.mode));
            dataFactory.applyTo(actionComboViewer.getControl());
        }
        {
            anonymizeStacktracesButton = new Button(container, SWT.CHECK);
            anonymizeStacktracesButton.setText("Anonymize stacktraces");
            anonymizeStacktracesButton.setSelection(wizardPreferences.anonymize);
            anonymizeStacktracesButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    wizardPreferences.anonymize = anonymizeStacktracesButton.getSelection();
                }
            });
            clearMessagesButton = new Button(container, SWT.CHECK);
            clearMessagesButton.setText("Clear messages");
            clearMessagesButton.setSelection(wizardPreferences.clearMessages);
            clearMessagesButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    wizardPreferences.clearMessages = clearMessagesButton.getSelection();
                }
            });

        }
        {
            Composite feedback = new Composite(container, SWT.NONE);
            layoutFactory.applyTo(feedback);
            dataFactory.grab(true, true).applyTo(feedback);
            {
                Link feedbackLink = new Link(feedback, SWT.NONE);
                dataFactory.align(SWT.BEGINNING, SWT.END).applyTo(feedbackLink);
                feedbackLink.setText("<a>Learn more...</a>");
                feedbackLink.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        Browsers
                                .openInExternalBrowser("https://docs.google.com/document/d/14vRLXcgSwy0rEbpJArsR_FftOJW1SjWUAmZuzc2O8YI/pub");
                    }
                });
            }

            {
                Link feedbackLink = new Link(feedback, SWT.NONE);
                dataFactory.align(SWT.END, SWT.END).applyTo(feedbackLink);
                feedbackLink.setText("<a>Provide feedback...</a>");
                feedbackLink.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent e) {
                        Browsers
                                .openInExternalBrowser("https://docs.google.com/a/codetrails.com/forms/d/1wd9AzydLv_TMa7ZBXHO7zQIhZjZCJRNMed-6J4fVNsc/viewform");
                    }
                });
            }

        }
        setControl(container);
    }
}