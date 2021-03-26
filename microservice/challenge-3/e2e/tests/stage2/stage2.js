import {After, Given, Then, When} from "cypress-cucumber-preprocessor/steps"
import configs from "../../configs"

const getRandomInt = (max) => Math.floor(Math.random() * Math.floor(max))

const createTank = () => {
    return cy.request({
        method: 'POST',
        url: `${configs.boosterUrl}/tanks`,
        body: {
            title: `${getRandomInt(100000)}_${getRandomInt(100000)}`,
            archived: false,
            fuel: [
                {
                    title: "Fuel",
                    priority: "0",
                    done: false
                }
            ]
        },
        failOnStatusCode: false
    }).then(response => {
        expect(response.status).to.eq(201)
        return response.body
    })
}

const getFuelIds = (tankTitle) => {
    return cy.request({
        method: 'GET',
        url: `${configs.boosterUrl}/tanks/${tankTitle}/fuel`,
        failOnStatusCode: false
    }).then(response => {
        expect(response.status).to.eq(200)
        return response.body.map(fuel => fuel.ID)
    })
}

const consumeFuel = (tankTitle, fuelId) => {
    return cy.request({
        method: 'PUT',
        url: `${configs.boosterUrl}/tanks/${tankTitle}/fuel/${fuelId}/complete`,
        body: {},
        failOnStatusCode: false
    })
}

const tanksTitle = () => {
    return cy.get("@tank1_response").then(tank1 => {
        return cy.get("@tank2_response").then(tank2 => {
            return [tank1.title, tank2.title]
        })
    })
}

const deleteTank = tank => cy.request('DELETE', `${configs.boosterUrl}/tanks/${tank.title}`).then(response => {
    expect(response.status).to.eq(204)
    return response.body
})

Given('2 tanks created with fuel', () => {
    createTank().as("tank1_response")
    createTank().as("tank2_response")
})

Given('all the fuel was consumed', () => {
    tanksTitle().each((title) => {
        return getFuelIds(title).each((id) => consumeFuel(title, id))
    })
})

When('trying to execute stage 2', () => {
    tanksTitle()
        .then(tanks => {
            const titles = tanks.join(",")
            return cy.exec(`docker-compose -f ../docker-compose.yml run -e TANKS=${titles} stage2`, {failOnNonZeroExit: false})
        })
        .then(out => {
            return cy.wrap(out.stdout).as("exec_stdout")
        })
})

Then('stage2 app logs {string}', (message) => {
    cy.get("@exec_stdout").then(stdout => {
        expect(stdout).to.contains(message)
    })
})

After(() => {
    cy.get("@tank1_response").then(deleteTank)
    cy.get("@tank2_response").then(deleteTank)
})
