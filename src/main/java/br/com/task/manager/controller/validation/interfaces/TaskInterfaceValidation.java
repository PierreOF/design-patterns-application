package br.com.task.manager.controller.validation.interfaces;

import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.model.Task;

public interface TaskInterfaceValidation {
    ResultValidationEnum validateTask(Task task);
}
