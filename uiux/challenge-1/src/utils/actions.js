

const inject = type => (action) => {
  if (typeof action === 'object') {
    return {
      ...action,
      type: `${action.type}_${type}`
    }
  }

  return `${action}_${type}`
}

export const resolve = inject('FULFILLED')
export const reject = inject('REJECTED')
export const pending = inject('PENDING')
