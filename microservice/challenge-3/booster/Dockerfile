FROM golang:1.16-alpine AS BUILD
WORKDIR /app
COPY . .
RUN CGO_ENABLED=0 go build

FROM scratch
WORKDIR /app
COPY --from=BUILD /app/booster* .
CMD ["./booster"]
