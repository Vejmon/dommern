import {http, HttpResponse} from 'msw'
import active from './active.json'

let sse_active = active;

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
  http.get('/linje/sse', ({ request }) => {
    sse_active[2].personalBest.tid += 11;
    const stream = new ReadableStream({
      start(controller) {
        controller.enqueue(new TextEncoder()
            .encode(`data: ${JSON.stringify(sse_active)}\n\n`));
      },
    })

    return new Response(stream, {
      headers: {
        connection: 'keep-alive',
        'content-type': 'text/event-stream',
        'cache-control': 'no-cache',
      },
    })
  }),
]