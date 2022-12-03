package handler;

public enum HandlerState {

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
    ANSWER_WAITING {
        @Override
        public HandlerState nextState(CommandType command) {
            return COMMAND_WAITING;
        }
    },

    TASK_TYPE_WAITING {
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

    public static void main(String[] args) {
        HandlerState state = HandlerState.ANSWER_WAITING;
        System.out.println(state);
        state.nextState(CommandType.TASKS);
        System.out.println(state);
    }

    }
