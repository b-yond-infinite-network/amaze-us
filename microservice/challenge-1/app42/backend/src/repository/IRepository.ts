export default interface IRepository<T> {
    getAll(): T[];

    add(model: T): T;
}
