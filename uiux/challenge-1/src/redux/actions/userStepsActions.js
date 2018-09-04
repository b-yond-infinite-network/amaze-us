export const SET_STEP = 'userSteps:setStep';

export function setStep(newStep) {
    return {
        type: SET_STEP,
        payload: newStep
    }
}