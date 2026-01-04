import { http, HttpResponse } from 'msw'

export const handlers = [
  // Intercepts GET requests to the health check endpoint
  http.get('*/actuator/health', () => {
    return HttpResponse.json({
      status: 'UP'
    })
  }),
]