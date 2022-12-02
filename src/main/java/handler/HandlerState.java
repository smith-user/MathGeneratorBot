package handler;

public enum HandlerState {

    COMMAND_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            if (command == CommandType.TASKS)
                return TASK_TYPE_WAITING;
            if (command == CommandType.ANSWERS)
                return ANSWER_WAITING;
            if (command == CommandType.SOLVE)
                return USERS_TASK_WAITING;
            return this;
        }
    },
    ANSWER_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return COMMAND_WAITING;
        }
    },

    TASK_TYPE_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return NUMBER_OF_TASKS_WAITING;
        }
    },

    NUMBER_OF_TASKS_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return COMMAND_WAITING;
        }
    },

    USERS_TASK_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return COMMAND_WAITING;
        }
    };

    public abstract HandlerState nextState(CommandType command);
    }
