package br.com.task.manager.controller.validation;

import br.com.task.manager.controller.validation.interfaces.TaskInterfaceValidation;
import br.com.task.manager.model.Task;

import java.util.Date;

public class TaskValidation implements TaskInterfaceValidation {

    @Override
    public ResultValidationEnum validateTask(Task task) {
        if (task.getTitulo() == null || task.getTitulo().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getDescricao() == null || task.getDescricao().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getStatus() == null) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getIdUsuario() < 0) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getPriority() == null) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }
}
