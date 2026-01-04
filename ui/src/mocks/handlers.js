import {http, HttpResponse} from 'msw'
import active from './active.json'


export const handlers = [
  // Intercepts requests to the backend
  http.get('/actuator/health', () => {
    return HttpResponse.json({
      status: 'UP'
    })
  }),
  http.get('/linje', () => {
    return HttpResponse.json(active)
  }),
]