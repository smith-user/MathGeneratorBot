package handler;

/**
 * Класс, содержащий состояния обработчика.
 */
public enum HandlerState {
    /**
     * Обработчик ожидает одну из доступных комманд {@code CommandType}
     */
    COMMAND_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            switch (command) {
                case TASKS -> {
                    return TASK_TYPE_WAITING;
                }
                case ANSWERS -> {
                    return ANSWER_WAITING;
                }
                case SOLVE -> {
                    return USERS_TASK_WAITING;
                }
                default -> {
                    return this;
                }
            }
        }
    },
    /**
     * Обработчкик ожидает ответы для задач, сгененрированных командой {@code CommandType.TASKS}
     */
    ANSWER_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return COMMAND_WAITING;
        }
    },

    /**
     * Обработчик ожидает тип {@code TaskGenerator.MathTaskTypes}
     * и количество задач для команды {@code CommandType.TASKS}
     */
    TASK_TYPE_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return COMMAND_WAITING;
        }
    },
    /**
     * Обработчик ожидает пользовательскую задачу для команды {@code CommandType.SOLVE}
     */
    USERS_TASK_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return COMMAND_WAITING;
        }
    };

    public abstract HandlerState nextState(CommandType command);

    public static void main(String[] args) {
        HandlerState state = HandlerState.ANSWER_WAITING;
        System.out.println(state);
        state.nextState(CommandType.TASKS);
        System.out.println(state);
    }

    }
