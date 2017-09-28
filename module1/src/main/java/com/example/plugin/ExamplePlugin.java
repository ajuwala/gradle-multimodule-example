package com.example.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ExamplePlugin implements Plugin<Project> {

    private static final String MESSAGE = "This is the default message.";
    private static final String EXTENSION_NAME = "exampleExtension";
    private static final String TASK_PRINT_MESSAGE = "printMessage";

    private Project project;

    @Override
    public void apply(Project project) {
        this.project = project;
        configureExtension();
        defineTasks();

        project.afterEvaluate(p -> configureTaskDependencies(p));
    }

    private void configureTaskDependencies(Project project) {
        project.getTasks().findByName("compileJava").getDependsOn().add(TASK_PRINT_MESSAGE);
    }

    private void defineTasks() {
        project.getTasks().create(TASK_PRINT_MESSAGE, ExampleTask.class);
    }

    private void configureExtension() {
        PluginExtension pluginExtension = new PluginExtension();
        pluginExtension.setMessage(MESSAGE);
        this.project.getExtensions().add(EXTENSION_NAME, pluginExtension);
    }
}
